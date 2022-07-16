package io.github.fengzaiyao.plugin.jwt.model;

import java.util.Optional;

public class UserAuthContextHolder {

    private static ThreadLocal<LoginAuthUserInfoDTO> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前上下文用户
     * LoginAuthUserInfo loginUserInfo = UserAuthContextHolder.getCurrentUser().orElseThrow(() -> new Exception("用户未登录"));
     */
    public static Optional<LoginAuthUserInfoDTO> getCurrentUser() {
        return Optional.ofNullable(threadLocal.get());
    }

    public static void setCurrentUser(LoginAuthUserInfoDTO loginUserInfo) {
        if (threadLocal.get() == null) {
            threadLocal.set(loginUserInfo);
        }
    }

    public static void clear() {
        threadLocal.remove();
    }
}
