package io.github.fengzaiyao.plugin.jwt.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.fengzaiyao.plugin.jwt.base.JwtPluginTokenUtil;
import io.github.fengzaiyao.plugin.jwt.model.LoginAuthUserInfoDTO;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtAuthTokenUtil extends JwtPluginTokenUtil {

    private Gson gson = new GsonBuilder().create();

    private final String CLAIM_KEY_USER = "user";

    private final String CLAIM_KEY_CREATED = "create-time";

    private final JwtAuthProperties properties;

    public JwtAuthTokenUtil(JwtAuthProperties properties) {
        super(properties);
        this.properties = properties;
    }

    public JwtAuthProperties getProperties() {
        return properties;
    }

    public String generateToken(LoginAuthUserInfoDTO userInfo) {
        return generateToken(userInfo, properties.getExpiration());
    }

    public String generateRefreshToken(LoginAuthUserInfoDTO userInfo) {
        return generateToken(userInfo, properties.getRefreshExpiration());
    }

    private String generateToken(LoginAuthUserInfoDTO userInfo, Long addTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER, userInfo);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, addTime);
    }

    public LoginAuthUserInfoDTO getUserInfoFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (!Objects.isNull(claims)) {
            return gson.fromJson(gson.toJson(claims.get(CLAIM_KEY_USER)), LoginAuthUserInfoDTO.class);
        }
        return null;
    }

    public String refreshToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (isTokenExpired(token)) {
            return null;
        }
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, properties.getExpiration());
    }
}
