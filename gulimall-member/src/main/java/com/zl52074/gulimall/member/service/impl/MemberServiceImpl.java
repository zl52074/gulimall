package com.zl52074.gulimall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl52074.gulimall.member.dao.MemberLevelDao;
import com.zl52074.gulimall.member.entity.MemberLevelEntity;
import com.zl52074.gulimall.member.exception.PhoneExistException;
import com.zl52074.gulimall.member.exception.UsernameExistException;
import com.zl52074.gulimall.member.service.MemberLevelService;
import com.zl52074.gulimall.member.vo.MemberUserLoginVo;
import com.zl52074.gulimall.member.vo.MemberUserRegisterVo;
import com.zl52074.gulimall.member.vo.SocialUser;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.Query;

import com.zl52074.gulimall.member.dao.MemberDao;
import com.zl52074.gulimall.member.entity.MemberEntity;
import com.zl52074.gulimall.member.service.MemberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberUserRegisterVo vo) {

        MemberEntity memberEntity = new MemberEntity();

        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", "1"));
        memberEntity.setLevelId(levelEntity.getId());

        //设置其它的默认信息
        //检查用户名和手机号是否唯一。感知异常，异常机制
        checkPhoneUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserName());

        memberEntity.setNickname(vo.getUserName());
        memberEntity.setUsername(vo.getUserName());
        //密码进行MD5加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);
        memberEntity.setMobile(vo.getPhone());
        memberEntity.setGender(0);
        memberEntity.setCreateTime(new Date());

        //保存数据
        this.baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException {

        Integer phoneCount = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));

        if (phoneCount > 0) {
            throw new PhoneExistException();
        }

    }

    @Override
    public void checkUserNameUnique(String userName) throws UsernameExistException {

        Integer usernameCount = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));

        if (usernameCount > 0) {
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberUserLoginVo vo) {

        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        //1、去数据库查询 SELECT * FROM ums_member WHERE username = ? OR mobile = ?
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", loginacct).or().eq("mobile", loginacct));

        if (memberEntity == null) {
            //登录失败
            return null;
        } else {
            //获取到数据库里的password
            String password1 = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //进行密码匹配
            boolean matches = passwordEncoder.matches(password, password1);
            if (matches) {
                //登录成功
                return memberEntity;
            }
        }

        return null;
    }

    @Override
    public MemberEntity login(SocialUser socialUser){

        //具有登录和注册逻辑
        String uid = socialUser.getUid();

        //1、判断当前社交用户是否已经登录过系统
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));

        if (memberEntity != null) {
            //这个用户已经注册过
            //更新用户的访问令牌的时间和access_token
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setAccessToken(socialUser.getAccess_token());
            update.setExpiresIn(socialUser.getExpires_in());
            this.baseMapper.updateById(update);

            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            return memberEntity;
        } else {
            //2、没有查到当前社交用户对应的记录我们就需要注册一个
            MemberEntity register = new MemberEntity();
            //3、查询当前社交用户的社交账号信息（昵称、性别等）
            // Map<String,String> query = new HashMap<>();
            // query.put("access_token",socialUser.getAccess_token());
            // query.put("uid",socialUser.getUid());
            // HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.weibo.com/2/users/show.json")
                    .queryParam("access_token", "2.00mRW3bH0muHCLe424ad5acb5ZN8EC")
                    .queryParam("uid", "6965021582");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, String.class);
            // if (response.getStatusLine().getStatusCode() == 200) {
            if (response.getStatusCode() == HttpStatus.OK) {
                //查询成功
                // String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(response.getBody());
                String name = jsonObject.getString("name");
                String gender = jsonObject.getString("gender");
                String profileImageUrl = jsonObject.getString("profile_image_url");

                register.setNickname(name);
                register.setGender("m".equals(gender)?1:0);
                register.setHeader(profileImageUrl);
                register.setCreateTime(new Date());
                register.setSocialUid(socialUser.getUid());
                register.setAccessToken(socialUser.getAccess_token());
                register.setExpiresIn(socialUser.getExpires_in());

                //把用户信息插入到数据库中
                this.baseMapper.insert(register);

            }
            return register;
        }

    }
}
