package io.github.fengzaiyao.plugin.jwt.base;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JwtPluginTokenUtil {

    private final JwtPluginProperties jwtPluginProperties;

    private final Map<String, SignatureAlgorithm> signatureAlgorithm = initSignatureAlgorithm();

    public JwtPluginTokenUtil(JwtPluginProperties jwtPluginProperties) {
        this.jwtPluginProperties = jwtPluginProperties;
    }

    /**
     * 生成Token
     *
     * @param claims  token负载,额外的信息
     * @param addTime token过期时长
     */
    public final String generateToken(Map<String, Object> claims, long addTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(addTime))
                .signWith(generateSignatureAlgorithm(), jwtPluginProperties.getSecret())
                .compact();
    }

    /**
     * 从token中获取负载信息
     *
     * @param token token本身
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtPluginProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException(e.getMessage(), e);
        }
        return claims;
    }

    /**
     * 判断token是否已经过期
     *
     * @param token token本身
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return Optional.ofNullable(expiredDate).map(obj -> obj.before(new Date())).orElse(true);
    }

    /**
     * 初始化所有加密算法
     */
    private Map<String, SignatureAlgorithm> initSignatureAlgorithm() {
        SignatureAlgorithm[] signatureAlgorithms = SignatureAlgorithm.values();
        ConcurrentHashMap<String, SignatureAlgorithm> saMap = new ConcurrentHashMap<>(signatureAlgorithms.length);
        for (SignatureAlgorithm algorithm : signatureAlgorithms) {
            saMap.put(algorithm.getValue(), algorithm);
        }
        return saMap;
    }

    /**
     * 生成token过期时间,如果当前时间超过改时间,token被视为过期
     *
     * @param addTime token过期时长
     */
    private Date generateExpirationDate(long addTime) {
        return new Date(System.currentTimeMillis() + addTime);
    }

    /**
     * 选择默认加密算法
     */
    private SignatureAlgorithm generateSignatureAlgorithm() {
        SignatureAlgorithm al = signatureAlgorithm.get(jwtPluginProperties.getSignatureAlgorithm());
        return Objects.isNull(al) ? SignatureAlgorithm.HS512 : al;
    }

    /**
     * 获取token过期时间
     *
     * @param token token本身
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Optional.ofNullable(claims).map(Claims::getExpiration).orElse(null);
    }
}
