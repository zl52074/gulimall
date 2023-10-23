package com.zl52074.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.member.entity.MemberEntity;
import com.zl52074.gulimall.member.exception.PhoneExistException;
import com.zl52074.gulimall.member.exception.UsernameExistException;
import com.zl52074.gulimall.member.vo.MemberUserLoginVo;
import com.zl52074.gulimall.member.vo.MemberUserRegisterVo;
import com.zl52074.gulimall.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 09:04:48
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberUserRegisterVo vo) throws PhoneExistException, UsernameExistException;

    public void checkUserNameUnique(String userName) throws UsernameExistException;

    public void checkPhoneUnique(String phone) throws PhoneExistException;

    MemberEntity login(MemberUserLoginVo vo);

    MemberEntity login(SocialUser socialUser);
}

