package io.github.fengzaiyao.plugin.jwt.config;

import io.github.fengzaiyao.plugin.jwt.base.JwtPluginProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.jwt.plugin")
public class JwtAuthProperties extends JwtPluginProperties {

    /**
     * 刷新token的过期时间
     */
    private Long refreshExpiration;

    /**
     * 刷新token的key,前端传过来的key
     */
    private String refreshTokenKey = "Refresh-Authorization";
}
