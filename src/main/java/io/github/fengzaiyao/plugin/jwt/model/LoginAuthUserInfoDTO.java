package io.github.fengzaiyao.plugin.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginAuthUserInfoDTO {
    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 用户编号(用户ID字符串形式)
     */
    private String userNo;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 账号密码
     */
    private Integer password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录的IP地址
     */
    private String loginIp;

    /**
     * 登录来源 {@see LoginFromEnums}
     */
    private Integer loginFrom;
}
