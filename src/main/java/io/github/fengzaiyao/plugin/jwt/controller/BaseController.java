package io.github.fengzaiyao.plugin.jwt.controller;

import io.github.fengzaiyao.plugin.jwt.enums.LoginFromEnums;
import io.github.fengzaiyao.plugin.jwt.model.AccessTokenDTO;
import io.github.fengzaiyao.plugin.jwt.model.LoginAuthUserInfoDTO;
import io.github.fengzaiyao.plugin.jwt.utils.IpUtil;
import io.github.fengzaiyao.plugin.jwt.config.JwtAuthTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    protected JwtAuthTokenUtil jwtAuthTokenUtil;

    @Autowired
    protected HttpServletRequest request;

    protected AccessTokenDTO generateAccessToken(LoginAuthUserInfoDTO loginUserInfo) {
        return generateAccessToken(loginUserInfo, LoginFromEnums.USER);
    }

    protected AccessTokenDTO generateAdminAccessToken(LoginAuthUserInfoDTO loginUserInfo) {
        return generateAccessToken(loginUserInfo, LoginFromEnums.ADMIN);
    }

    private AccessTokenDTO generateAccessToken(LoginAuthUserInfoDTO loginUserInfo, LoginFromEnums loginFrom) {
        loginUserInfo.setLoginFrom(loginFrom.code());
        loginUserInfo.setLoginIp(IpUtil.getRealIp(request));
        return doGenerateAccessToken(loginUserInfo);
    }

    private AccessTokenDTO doGenerateAccessToken(LoginAuthUserInfoDTO loginUserInfo) {
        String token = jwtAuthTokenUtil.generateToken(loginUserInfo);
        String refreshToken = jwtAuthTokenUtil.generateRefreshToken(loginUserInfo);
        String tokenHead = jwtAuthTokenUtil.getProperties().getTokenHead();
        return AccessTokenDTO.builder()
                .token(tokenHead + token)
                .refreshToken(tokenHead + refreshToken)
                .tokenHead(tokenHead)
                .build();
    }
}
