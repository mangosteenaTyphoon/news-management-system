package com.qy.news.satoken;


import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 山竹
 * @version 1.0
 * @description: TODO
 * @date 2023/3/11 9:47
 */
public class SaTokenConfigure implements WebMvcConfigurer, StpInterface {



    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            //登录认证
            SaRouter.match("/**", () -> StpUtil.checkLogin());
            // 角色认证 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
//            SaRouter.match("/user/**", () -> StpUtil.checkRoleOr("admin", "super-admin"));
            //权限认证
//            SaRouter.match("/menu/**", () -> StpUtil.checkPermission("company"));
            SaRouter.match("/user/**", () -> StpUtil.checkRoleOr("admin"));
//            SaRouter.match("/user/**", () -> StpUtil.checkPermission("admin"));
//            SaRouter.match("/role/**", () -> StpUtil.checkPermission("admin"));

        })).addPathPatterns("/**").excludePathPatterns(
                "/auth/**", "/operate_log/add", "/doc.html", "/webjars/**", "/swagger-resources", "/actuator/**");
    }


    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
