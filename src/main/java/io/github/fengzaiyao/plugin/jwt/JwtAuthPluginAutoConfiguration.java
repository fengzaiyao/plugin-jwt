package io.github.fengzaiyao.plugin.jwt;

import io.github.fengzaiyao.plugin.jwt.config.JwtAuthProperties;
import io.github.fengzaiyao.plugin.jwt.config.JwtAuthTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtAuthProperties.class)
public class JwtAuthPluginAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JwtAuthTokenUtil.class)
    public JwtAuthTokenUtil jwtAuthTokenUtil(JwtAuthProperties jwtAuthProperties) {
        return new JwtAuthTokenUtil(jwtAuthProperties);
    }
}
