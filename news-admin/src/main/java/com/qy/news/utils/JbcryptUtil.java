package com.qy.news.utils;

import cn.dev33.satoken.secure.BCrypt;

/**
 * @author 山竹
 * @version 1.0
 * @description: TODO
 * @date 2023/2/15 16:30
 */

public class JbcryptUtil {
    /**
     * 密码加密
     */
    public static String bcryptPwd(String pwd) {
        return BCrypt.hashpw(pwd, BCrypt.gensalt(12));
    }

    /**
     * 验证密码正确性
     * pwd 用户原来的密码    hashed用户新密码
     */
    public static boolean checkPwd(String pwd, String hashed) {
        try {
            return BCrypt.checkpw(pwd, hashed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

