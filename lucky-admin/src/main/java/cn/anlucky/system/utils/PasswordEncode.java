package cn.anlucky.system.utils;

import cn.dev33.satoken.secure.BCrypt;

public class PasswordEncode {

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encode(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt(10));
    }

    /**
     * 比较加密后的密码和明文密码是否一致
     * @param password
     * @param encodedPassword
     * @return
     */
    public static boolean matches(String password, String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }

}
