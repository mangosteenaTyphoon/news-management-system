package com.qy.news.service;

import com.qy.news.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 登录认证逻辑处理
     *
     * @param reqDTO
     * @return
     */
    Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO);


}
