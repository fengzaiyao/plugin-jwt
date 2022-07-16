package io.github.fengzaiyao.plugin.jwt.enums;

public enum LoginFromEnums {

    ADMIN(0, "管理员端"),

    USER(1, "用户端");

    private int code;
    private String desc;

    LoginFromEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
