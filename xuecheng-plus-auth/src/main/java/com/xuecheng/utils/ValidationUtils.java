package com.xuecheng.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    // 校验手机号：11位，第二位为3-9
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    
    // 校验邮箱：常规邮箱格式
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    
    // 校验验证码：6位纯数字
    private static final Pattern CODE_PATTERN = Pattern.compile("^\\d{6}$");
    
    // 校验密码：6-20位，且必须同时包含字母和数字
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidCode(String code) {
        if (code == null) return false;
        return CODE_PATTERN.matcher(code).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}