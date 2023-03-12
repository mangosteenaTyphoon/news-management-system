package com.qy.news.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.qy.news.constant.AuthConst;
import com.qy.news.entity.SysUser;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.mapper.SysUserMapper;
import com.qy.news.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.news.utils.JbcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.JdbcRowSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO) {
        Map<String, Object> returnMap = new HashMap<>();
        String account = reqDTO.getAccount();
        String password = reqDTO.getPassword();
        SysUser user = userMapper.selectUserAuthInfo(account);
        if(user !=null && !StrUtil.isEmptyIfStr(user.getId())){
            if(!JbcryptUtil.checkPwd(password,user.getPassword())){
                returnMap.put(AuthConst.FLAG,AuthConst.FLAG_ONE_VAL);
            } else {
                returnMap.put(AuthConst.ID, user.getId());
                returnMap.put(AuthConst.USER,user);
                returnMap.put(AuthConst.FLAG,AuthConst.FLAG_TWO_VAL);
                returnMap.put("token", StpUtil.getTokenValue());
                System.out.println("token:" + StpUtil.getTokenValue());
            }
        } else {
            returnMap.put(AuthConst.FLAG, AuthConst.FLAG_ZERO_VAL);
        }

        return returnMap;
    }
}
