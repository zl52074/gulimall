package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 *沙箱官网 https://open.alipay.com/develop/sandbox/app
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "9021000131625510";

    public static String merchant_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqtoZym/Tm6pOzJ8wMmeHIJ5/5sueETzD+XZg/yN70Vuxtc1t0+1CgiHdTp3A0qATEn6N4LjYRbmnEke1zsLe4vLa5tq5J1lbR2RwGOAHBsYcCUD42X/pFD2EuEfqqCFxJyCciNBWFBeeWTIYtIela9C3bKxKSxIykWgPakP5Fhm1GRi3bh4Sqj1zZgEsN9OkitDtIjjS07WLNCeo2BRNP3kTuDqqrPyH8cm+p+afLtcWCnBVK/mTcBhkuxZJo5TZgVz/51XP/cYwBKJKnVTOUE4mzL+1usDLG39uzZv+utOzZcmFLtKXf+pGLGdmmbGhzn9CLjp6cM4SEtjhOvLd4QIDAQAB";

	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCq2hnKb9Obqk7MnzAyZ4cgnn/my54RPMP5dmD/I3vRW7G1zW3T7UKCId1OncDSoBMSfo3guNhFuacSR7XOwt7i8trm2rknWVtHZHAY4AcGxhwJQPjZf+kUPYS4R+qoIXEnIJyI0FYUF55ZMhi0h6Vr0LdsrEpLEjKRaA9qQ/kWGbUZGLduHhKqPXNmASw306SK0O0iONLTtYs0J6jYFE0/eRO4Oqqs/Ifxyb6n5p8u1xYKcFUr+ZNwGGS7FkmjlNmBXP/nVc/9xjAEokqdVM5QTibMv7W6wMsbf27Nm/6607NlyYUu0pd/6kYsZ2aZsaHOf0IuOnpwzhIS2OE68t3hAgMBAAECggEAPzKh66Hl0mTy9PnFaD+nWkeG5CZZd+LUUwiY5IeJm22hlLBCeev/eAdyx0vPMZoItASgEooNZe1AgaCI/cxTiftvQYmSh52kCAp89JaZwsWxRTMFWd7YpqYC2++uOzbzlheYD3uQ+6mo5zGCtfSfGfyAfPBWIuK+Vc76NMKj9qo87lEr656S0kH1jzhYSlN28SEuX5vkOic2o8eLYJ+Kj4e4OakdGlbaNZ57lcpb+G/q7a2C1ZfxGkv9z9wE+SSOkgtBEmbyWfUQ0sOJ+w8lovwI1mTo0990r+tG70ppgRuEdgFbS7YEyAPBjr3HOS5UfHPobaaodTMOyQYQMoOs6QKBgQDWyXXT24Z1lAjxl/nA4QFT7DSKUd1POCeE1RVtjqhfksSmnjD2u1xdh/3LmNsxY8oBXZ0r659ieK2bqL1eeSx/bQfjKLqLDYgt3v93Bka/atb66csADPWLX0h/lCHWl4qmUDSpq8aQjCUwkWLv0UUllH2CuI3j6I7RC5Tc20R+awKBgQDLooLz07N84/j95UiUr40Bbts+1mqNs+QwCAhAJIklgW0a8zRbpTZuancYoQd7XXrUMunWySlwJ/YYIHUga0CLAkwqpTg6WPv6VZNQ+bI9l5O6YZM/VUvt8U/DVJTdm+9rus7cg7AVwdtUI0A2wcaLyLaHaS45RCOWJIIpVAuP4wKBgFic2Q3Evd54apAx4QpgSgnzo2GFkMaEJlm5B5QhjPmRNayACzdf6SV1W5HJyHTB9K9HjfZ2yVT48LcNkMMPPvp7Ywum2/9OX+Z83+ydHEcQrwzps6SbtBoB/4kS9jrwXnVrFa8CfXNZPmahNK44KkzszDNILNHgOjuoiYvssn9tAoGBAIRmVoSYSeRTCYpzMJzUA3dCD7Usjj0yEJSWNF8YIj/Lhhf2tz07D0f9WxAdaxLv9bPQoq+vPY0Zvm1zdRQuB9oaFVHI+OUfBD6As3b70sMvMbMGTEnqeKLtujHDZcMs5x+BZ4yC5tNOpv9uqAqyNRif2OX0IkTDo+u0MxkR9y6HAoGAMc47QnJ8GqU4BET7MqKJobLZYXPKx2TsuIJuZjh8lt13wwuRf+mFcPP7gKq8uA+1TmKd+aXtbcQLdZhp4SgkKvOfgoA643Z3TJPP2zrh++Mr0EpFXYV07Y44fFB04dcOEdYZ4Wg9ZfXaiXFlI84i2QhzU+9pwqdRdoP9/SJWot4=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlzyWpQyVa2vF+k20ngW9arm4bI30tK8G3GfECjcVvbjUkmyWBN0BhNrwpAm28+6RSdQ/CJK4W+io7J2lcw2cUmCixkHm6Af7lFWVkppoLAeEqBfFHt6cEKrZkbpXt9usMJjzgTDvPc5C6aQxA6TdSdn1ioM02/ePolOB54QbX5wVLKLP+Kuk8nnYFK+ZQKkvZytruqt985Nw18+lyzROGCZdAUYPpMKtfQFsLxYwwjsGMqeRprvjRKrIroUYZNrCquhiqaoey9qPJaEOuWqhjZDkBu3k1aUU8/bi3+xZSn8LDzSFinCI7s1SQZKh7S0AyfYYQMifvtiDRcjzq57OrQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

