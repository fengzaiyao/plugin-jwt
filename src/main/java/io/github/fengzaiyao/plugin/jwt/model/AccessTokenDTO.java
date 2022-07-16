package io.github.fengzaiyao.plugin.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccessTokenDTO {
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新访问令牌
     */
    private String refreshToken;

    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
}
