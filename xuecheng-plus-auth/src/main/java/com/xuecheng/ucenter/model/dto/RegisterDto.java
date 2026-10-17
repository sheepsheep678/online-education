package com.xuecheng.ucenter.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户注册/登录请求参数模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     * 建议在 JS 端使用 confirmPassword，这里兼容写为 confirmPwd
     */
    @JsonProperty("confirmpwd") // 保证前端传 confirmpwd 时能正确映射到此字段
    private String confirmPwd;

    /**
     * 验证码缓存 Key
     */
    @JsonProperty("checkcodekey")
    private String checkCodeKey;

    /**
     * 验证码
     */
    @JsonProperty("checkcode")
    private String checkCode;

}
