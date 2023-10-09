package com.zl52074.gulimall.thirdparty.util;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;

/**
 * @ClassName: RSAUtil
 * @description: 非对称加解密工具类
 * @create: 2020-09-26 10:55
 **/

public class RSAPemUtil {
    private static final String PRIVATE_KEY_PEM_PATH = "/rsa_private_key.pem";
    private static final String PUBLIC_KEY_PEM_PATH = "/rsa_public_key.pem";

    public static String readFile(String filePath){
        File f = new File(filePath);
        InputStream in = null;
        try {
            in = RSAPemUtil.class.getResourceAsStream(filePath);
            byte[] buf = new byte[1024];
            int len = -1;
            StringBuffer sb = new StringBuffer();
            while((len = in.read(buf)) != -1){
                sb.append(new String(buf,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                in = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        /*
        pkcs#8
        openssl genpkey -out rsa_private_key.pem -algorithm RSA -pkeyopt rsa_keygen_bits:1024
        openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
         */
        //公私钥全部为pem文件
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(PEM);
        String publicKey = readFile("/rsa_public_key.pem");
        String privateKey = readFile("/rsa_private_key.pem");
        config.setPublicKey(publicKey);
        config.setPrivateKey(privateKey);
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "gulimall-zl52074";
        String encrypted = encryptor.encrypt(message);
        System.out.println(encrypted);
        String decrypted = encryptor.decrypt(encrypted);
        System.out.println(decrypted);


// 公钥cer 私钥pem
//        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
//        config.setKeyFormat(PEM);
//        String privateKey = readFile("/pkcs8_rsa_private_key.pem");
//        // 获取公钥字符串
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream in = RSAPemUtil.class.getResourceAsStream("/rsa_public_key.cer");
//        X509Certificate cert = (X509Certificate)cf.generateCertificate(in);
//        PublicKey puk = cert.getPublicKey();
//        String pukstr = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(puk.getEncoded());
//        System.out.printf("pukstr message %s\n", pukstr);
//        config.setPublicKey(pukstr);
//
//        config.setPrivateKey(privateKey);
//        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
//        String message = "@Smc123";
//        String encrypted = encryptor.encrypt(message);
//        System.out.printf("Encrypted message %s\n", encrypted);
//        String decrypted = encryptor.decrypt(encrypted);
//        System.out.printf("Decrypted message %s\n", decrypted);
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String encrypt(String str){
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(PEM);
        String publicKey = readFile(PUBLIC_KEY_PEM_PATH);
        String privateKey = readFile(PRIVATE_KEY_PEM_PATH);
        config.setPublicKey(publicKey);
        config.setPrivateKey(privateKey);
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String encrypted = encryptor.encrypt(str);
        return encrypted;
    }

    /**
     * 解密
     * @param str
     * @return
     */
    public static String decrypt(String str){
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(PEM);
        String publicKey = readFile(PUBLIC_KEY_PEM_PATH);
        String privateKey = readFile(PRIVATE_KEY_PEM_PATH);
        config.setPublicKey(publicKey);
        config.setPrivateKey(privateKey);
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String decrypted = encryptor.decrypt(str);
        return decrypted;
    }
}
