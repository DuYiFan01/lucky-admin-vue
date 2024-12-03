package cn.anlucky.system.enums;

/**
 * 全局响应响应码
 */
public final class Code {
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "响应成功";

    public static final String ERROR_CODE = "-1";
    public static final String ERROR_MESSAGE = "响应失败";

    public static final String NO_LOGIN_CODE = "-401";
    public static final String NO_LOGIN_MESSAGE = "未登录";

    public static final String NO_PERM_CODE = "-403";
    public static final String NO_PERM_MESSAGE = "没有权限";

    public static final String NO_ROLE_CODE = "-405";
    public static final String NO_ROLE_MESSAGE = "没有角色";
}
