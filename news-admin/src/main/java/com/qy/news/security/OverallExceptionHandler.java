package com.qy.news.security;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.qy.news.result.R;
import com.qy.news.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 山竹
 * @version 1.0
 * @description: TODO
 * @date 2023/2/15 9:54
 */

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OverallExceptionHandler {

    /**
     * 用户接口基本鉴权
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotLoginException.class)
    public R handUserExceptionHandler(NotLoginException e) {
        e.printStackTrace();
        // 判断场景值，定制化异常信息
        String message = "";
        if (e.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (e.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (e.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (e.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (e.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }
        return R.error().code(ResultCode.TOKEN_ERROR.getCode()).message(ResultCode.TOKEN_ERROR.getMsg());
    }

    /**
     * 权限
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public R handNotPermissionExceptionHandler(NotPermissionException e) {
        e.printStackTrace();
        return R.error().code(ResultCode.PERM_ERROR.getCode()).message(e.getMessage());
    }

    /**
     * 角色
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotRoleException.class)
    public R handNotNotRoleExceptionHandler(NotRoleException e) {
        e.printStackTrace();
        return R.error().code(ResultCode.ROLE_ERROR.getCode()).message(ResultCode.ROLE_ERROR.getMsg());
    }

}
