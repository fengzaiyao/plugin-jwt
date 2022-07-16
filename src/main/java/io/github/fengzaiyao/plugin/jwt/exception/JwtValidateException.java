package io.github.fengzaiyao.plugin.jwt.exception;

public class JwtValidateException extends RuntimeException {

    public JwtValidateException(String message) {
        super(message);
    }

    public JwtValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
