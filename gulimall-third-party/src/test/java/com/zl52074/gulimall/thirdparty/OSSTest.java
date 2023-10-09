package com.zl52074.gulimall.thirdparty;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zl52074.gulimall.thirdparty.util.RSAPemUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/9 4:26
 */
@SpringBootTest
public class OSSTest {
    static {
        System.setProperty("jasypt.encryptor.privateKeyFormat","PEM");
        System.setProperty("jasypt.encryptor.privateKeyLocation","classpath:rsa_private_key.pem");
    }
    @Autowired
    private OSS ossClient;
    @Test
    public void test() throws Exception {

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String encryptedAccessKeyId = "agckgjGyvNzfNtI4xNwE7c1hbbLJOBImzoLX71115cbvjkHyLctXE5Lsn5ZPB2qTRrMJw08ysboUpPcfqGN7CdpCjNrxGQXjQUtbWbPwQUuAyNxiZMMmFoxDZsUNHkxvOSzahEfqP7lAIVEMUqMczrySa3heIP0ehRvMz/TB3uo=";
        String encryptedAccessKeySecret = "jx+eCOEJLOsIUya9sfDxc8Be6q380BFiqUyEncESBc5ljXHYjo9dXQzq9AJ8i5LF/KfA7aakYn0IS0KUxuh2WMAZUY46NRrjTDwgzuIlRgLj7hLbZIsejWSNlPYFSPKCe+nz36/S9VQ42FkD4BgJO29Q3OhTBrvUwYkNmTDSgRQ=";
        // 填写Bucket名称，例如examplebucket。
        String encryptedBucketName = "fDjfAAsNxja5d5BCWoUEikut80blnEjTESlhNZ4jMU4TLNZmSCwv08//pb/SjMJMpEfKj2dxbI3F6uKV39yhIgT1wtrn3nytA/dhnO/zvsuW0GsY/VU20YKB6Z1iC9whd+P1mMMvspYqS6SmIiUS6ffcJQiIrKg4WvUBugoa08Q=";
        // 创建OSSClient实例。
        // OSS ossClient = new OSSClientBuilder().build(endpoint, RSAPemUtil.decrypt(encryptedAccessKeyId),RSAPemUtil.decrypt(encryptedAccessKeySecret));

        try {
            String bucketName = RSAPemUtil.decrypt(encryptedBucketName);
            File file = new File("D:/test/test.txt");
            InputStream inputStream = new FileInputStream(file);
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(),inputStream);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传
            // PutObjectResult result = ossClient.putObject(putObjectRequest);
            //删除
            // ossClient.deleteObject(bucketName, "2023-10-09/2a0baaef-c48f-4d29-877f-8e0f500f7c7d_golden.png");
            List<String> keys = new ArrayList<String>();
            keys.add("2023-10-09/0132c127-1e1e-49cc-8bae-3b29ca8b9f8d_QQ截图20231009082252.png");

            ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType("url"));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                //关闭客户端
                ossClient.shutdown();
            }
        }
    }

}
