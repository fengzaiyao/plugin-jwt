package io.github.fengzaiyao.plugin.jwt.interceptor;

import io.github.fengzaiyao.plugin.jwt.annotation.RequireCurrentUser;
import io.github.fengzaiyao.plugin.jwt.config.JwtAuthProperties;
import io.github.fengzaiyao.plugin.jwt.exception.JwtValidateException;
import io.github.fengzaiyao.plugin.jwt.model.LoginAuthUserInfoDTO;
import io.github.fengzaiyao.plugin.jwt.model.UserAuthContextHolder;
import io.github.fengzaiyao.plugin.jwt.config.JwtAuthTokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UserAuthInterceptor extends HandlerInterceptorAdapter {

    private final JwtAuthTokenUtil jwtAuthTokenUtil;

    public UserAuthInterceptor(JwtAuthTokenUtil jwtAuthTokenUtil) {
        this.jwtAuthTokenUtil = jwtAuthTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截器会拦截静态资源,如果直接强转会报错
        if (handler instanceof ResourceHttpRequestHandler) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireCurrentUser methodAnnotation = handlerMethod.getMethodAnnotation(RequireCurrentUser.class);
        if (Objects.isNull(methodAnnotation) || methodAnnotation.needUser()) {
            JwtAuthProperties properties = jwtAuthTokenUtil.getProperties();
            String token = request.getHeader(properties.getTokenKey());
            if (!StringUtils.isEmpty(token)) {
                if (!token.startsWith(properties.getTokenHead())) {
                    return super.preHandle(request, response, handler);
                }
                if (properties.getTokenHead().length() <= token.length()) {
                    token = token.substring(properties.getTokenHead().length());
                }
                try {
                    LoginAuthUserInfoDTO userInfo = jwtAuthTokenUtil.getUserInfoFromToken(token);
                    UserAuthContextHolder.setCurrentUser(userInfo);
                } catch (Exception e) {
                    // 1.token已被篡改 2.token已经过期
                    throw new JwtValidateException(e.getMessage(), e);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserAuthContextHolder.clear();
        super.afterCompletion(request, response, handler, ex);
    }
}
