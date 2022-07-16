package io.github.fengzaiyao.plugin.jwt.base;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtPluginProperties {
    /**
     * jwt密文
     */
    private String secret;

    /**
     * token过期时长,单位毫秒
     */
    private Long expiration;

    /**
     * 加密签名算法
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS512.getValue();

    /**
     * 请求头的默认前缀
     */
    private String tokenHead = "Bearer";

    /**
     * token的key,前端传过来的key
     */
    private String tokenKey = "Authorization";
}
