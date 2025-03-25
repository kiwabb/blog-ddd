package com.jackmouse.system.utils;

/**
 * @ClassName RedisKeyUtil
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 14:33
 * @Version 1.0
 **/
public class RedisKeyUtil {
    private RedisKeyUtil() {
    }

    /**
     * 验证码Key.
     * @param uuid UUID
     */
    public static String getUsernamePasswordAuthCaptchaKey(String uuid) {
        return "auth:username-password:captcha:" + uuid;
    }

    public static String getMenuKey(String token) {
        return "menu:user:" + token;
    }

    /**
     * 布隆过滤器Key.
     */
    public static String getBloomFilterKey() {
        return "bloom:filter";
    }

    /**
     * 手机验证码Key.
     * @param mobile 手机号
     */
    public static String getMobileAuthCaptchaKey(String mobile) {
        return "auth:mobile:captcha:" + mobile;
    }

    /**
     * 邮箱验证码Key.
     * @param mail 邮箱
     */
    public static String getMailAuthCaptchaKey(String mail) {
        return "auth:mail:captcha:" + mail;
    }

    /**
     * 接口幂等性Key.
     * @param token 令牌
     */
    public static String getApiIdempotentKey(String token) {
        return "api:idempotent:" + token;
    }

    /**
     * 动态路由Key.
     */
    public static String getRouteDefinitionHashKey() {
        return "route:definition";
    }

    /**
     * IP缓存Key.
     * @param type 类型
     */
    public static String getIpCacheHashKey(String type) {
        return "ip:cache:" + type;
    }

}
