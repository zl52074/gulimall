package com.zl52074.gulimall.thirdparty.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;

import com.zl52074.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/9 7:06
 */
@RestController
public class OssController {
    @Autowired
    private OSS ossClient;
    @Value("${spring.cloud.alicloud.access-key}")
    private String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessKeySecret;
    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @RequestMapping("/oss/delete")
    public R delete(@RequestBody() String[] file){
        List<String> keys = Arrays.asList(file);
        ossClient.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(keys).withEncodingType("url"));
        return R.ok().put("data", Arrays.asList(file));
    }

    @RequestMapping("/oss/policy")
    public R policy(){

        Map<String, String> respMap = new LinkedHashMap<String, String>();

        // 填写Host名称，格式为https://bucketname.endpoint。
        String host = "https://"+bucket+"."+endpoint;
        // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
        String callbackUrl = "";
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dir = simpleDateFormat.format(new Date())+"/";

        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ossClient.shutdown();
        }
        return R.ok().put("data",respMap);
    }
}

