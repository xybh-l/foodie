package com.xybh.resources;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author a1353
 */
@Component
public class AlipayConfig {

    /**
     * 支付宝网关(https://openapi.alipay.com/gateway.do)
     * 沙盒网关(https://openapi.alipaydev.com/gateway.do)
     */
    public static String URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 创建应用时产生的应用号
     */
    public static String APP_ID = "2016092500593977";

    /**
     * 开发者私钥
     */
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCW5FlrI3KTlE+bcquGJpFczowYtdZoqJy7ZFS/nUeForBKzncK6hsGrML5DKo9ElE8WgDK3CiXgGYuHGuRjSkmYH53G28oPc0sxdOV45CvqchDf3debff9H2Q6P49/c4817ctccoDAUFHNP6UkLok9Of4XeoFSm57Daen9Ln0/uvNq9oCJP1Y/68gSCBRSXYwMNfyW8njmaZkDwQNYOXr8+FDyRYsG3NpN4FWZSFzhwG+74D+u+cxtkO0cKKIfZW/1GM+oWZ6Pna8s+aABhv66mURFeywo1VBGm3iApFhA4O1iyHQwMckkpFwqWeZcy7V/iBwYI/NfcajNoHQInSrRAgMBAAECggEAR9wqBY8VItlv7Bz5OOMnOSaYq6/1k1QxiEDo53RUunzXLlkTclw5UL14ATmZ3YnarD5EwFeG8VfR0cXxKJksbr1nnPuwvV5pVxhDgs+keTQHTrXmuE7w2lhX2DGq+kyjeFBuF/HkBTJ7Mp9L22ZaL5Ftw+qwqhG402GecNfyCCYaQJuJdvfvtYRLg+UpNJsKMDV8+zuiPyi5YFTZQuOBauw1FlpHFqYpE/bTXMIBxnk7WB6qNpVIdWJkY+b/XpB4jGPoBRZTGlpoYuzJ6eYHHWRySeVL80xi6akq/epP7NiHZgXKMteYog/XmJa0NXwdZHp0ljNLT+IsFX7oIGGawQKBgQDJxikyQjU81aUZHbWFAINdf1ZBe0k5TGYo2+498iS94hK4J3Y3sU1BmmYT+JUjmLxTR52XngCTA1unEGZq6MojP2VOmKClzbqn9aN/pXK7EDPs1MtV6/JOJ3qqH+S4+cKfbWxgAd3+wAKtWe2D0j45q4sQfFnHNxIGKzLJmtc/KQKBgQC/cY5ekjnfKtcIyKHAaXschQ06JYX7rCHlliZWvIBueZQO7sHcrmfPRxoqG21YNCZGY6iyow87BFy2ObqgWmkkXTY0E0VzKXf7ocJOmSdLHBtowrAiJyG0VuLj7K0aU5Lnz7wG2vg7vAGPxfq+njPb3xRB1AJDbQ4dEFSJhOGLaQKBgQCL3a4u5ZAr1Fj2eQ6b3IgzJpKvH3vZmSjzx8h07kYjzfN17fzXjDLmd9WsoFMZcsWQ4QLP17nELr64pvinwShlmItsGQa7CEoMJY0pbZGDNC8aS0OMEttK67ju9t7AjnkntF08i+7LqWPxsHWhO/rgVRY33ksJehYnaohxEcGFSQKBgG9ecxEHBc2SkWeVt6tEcY8Ew84YuBnRaCK0N3drz1b1OTF6qaJkfxVw6oKmU/Tk2YNHRIMvIpQVlI2Zhbz0+4dhBkBDGbrF2/uxfWTV6arjHfrSjz4+/3d2uCUZsRXImoRqL5rZVWECnPHznUu4orh8zL8Cw5JP5c74igU6HS9JAoGBAJyMl9Zm1REytMlWuuHD/fAonRoqctM+AF7GizejoIxx2C8Iv+Q7AXcNWBJTgYXa68Igyw9WXDr9n6UF/ClP9taSQ+kLx2ouEHvBSeRuo5OYiTAreWG5OWBvaXYL4VWsQbH0jcoiRKD5BdgW0aIv9yD4WOTG7933EKya61V9h6Sv";

    /**
     * 参数返回格式, 只支持json
     */
    public static String FORMAT = "json";

    /**
     * 编码集,支持GBK/UTF-8,根据自己工程编码进行调整
     */
    public static String CHARSET = "UTF-8";

    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiZ3TA49GCxJlAXoxJuQUepZD+VGGwiU6KYuRWaJWXzNekIkrkaeRquVUyR8KhB9jR0/569GSCJi/+UQl+reeZimlv2Wdsl2ZoX70jN7Xkpd405ifkFc9mJZ1kQbOsjEtA94EURe+u0XzqJqdpxpMGe8idtgdiic4i4vlHjKUsjHY1fJfH0/bz21+B+QaDGrH02upyWs2+CEjCb+gAxX49PHfuE5nsqvGRtGE9NxcNVLxQrPKccff2ZQQI4bLz+v3pzCODCIJcaQ/PxZleQA+P3U14vvav94VVNjw0ghrnRI3PZKIKUiKFGk5gr6MsCumXvT3KHQrhh1tT4EDfDvwIQIDAQAB";

    /**
     * 签名算法类型
     */
    public static String SIGN_TYPE = "RSA2";

    /**
     * 回调地址
     */
//    public static String RETURN_URL = "http://localhost:8089/alipayResult";
    public static String RETURN_URL = "http://payment.z.xybh.online:8089/foodie-payment/alipayResult";


    /**
     * 异步通知地址
     */
//    public static String NOTIFY_URL = "http://foodshop.xybh.online/payment/notice/alipay";
    public static String NOTIFY_URL = "http://payment.z.xybh.online:8089/foodie-payment/payment/notice/alipay";

    /**
     * 私有化构造方法
     */
    private AlipayConfig(){}

    private volatile static AlipayClient instance = null;

    /**
     * 双重校验锁,单例模式
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if(instance == null){
            synchronized (AlipayConfig.class){
                if(instance == null){
                    instance = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
                }
            }
        }
        return instance;
    }
}

