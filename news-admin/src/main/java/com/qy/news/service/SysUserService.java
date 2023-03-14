package com.qy.news.service;

import com.qy.news.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.entity.dto.UserIdReqDTO;
import com.qy.news.entity.dto.UserQueryDTO;
import com.qy.news.entity.dto.UserStatusDTO;
import com.qy.news.result.R;

import java.util.List;
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


    List<String> queryUserIdByRole(UserIdReqDTO reqDTO);

    /**
     * 注册处理逻辑
     *
     * @param reqDTO
     * @return
     */
    int saveUser(SysUser reqDTO);

    boolean updateUserStatus(UserStatusDTO reqDTO);

    R queryUserListByWordAndPage(UserQueryDTO reqDTO, Long current, Long limit);
}
