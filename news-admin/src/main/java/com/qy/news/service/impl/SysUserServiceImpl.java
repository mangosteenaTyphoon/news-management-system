package com.qy.news.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qy.news.constant.AuthConst;
import com.qy.news.constant.UserConst;
import com.qy.news.entity.SysUser;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.entity.dto.UserIdReqDTO;
import com.qy.news.mapper.SysUserMapper;
import com.qy.news.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.news.utils.JbcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.JdbcRowSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        Integer type = reqDTO.getType();
        String account = reqDTO.getAccount();
        String password = reqDTO.getPassword();
        SysUser user = userMapper.selectUserAuthInfo(account,type);
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

    @Override
    public List<String> queryUserIdByRole(UserIdReqDTO reqDTO) {
        return userMapper.selectUserIdByRole(reqDTO.getId());
    }

//    这里-1 代表注册失败 0代表有信息输入不全 1代表有账号、邮箱、手机号码重复
    @Override
    public int saveUser(SysUser sysUser) {
        if(StrUtil.isEmpty(sysUser.getUserName()) || StrUtil.isEmpty(sysUser.getPhone())
          || StrUtil.isEmpty(sysUser.getNickName() )||StrUtil.isEmpty(sysUser.getEmail())
          || StrUtil.isEmpty(sysUser.getPassword()) ) return 0;
        if(checkUserByUserNameAndEmailAndPhone(sysUser)) return 1;
        sysUser.setPassword(JbcryptUtil.bcryptPwd(sysUser.getPassword()));
        sysUser.setId(IdUtil.simpleUUID());
        sysUser.setStatus(UserConst.USER_STATUS_NORMAL);
        return 0 < userMapper.insert(sysUser) ? 2 : -1;
    }


/*
 * 根据用户账号、邮箱、电话进行查重
 */
    public boolean checkUserByUserNameAndEmailAndPhone(SysUser sysUser) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", sysUser.getUserName()).or().eq("email", sysUser.getEmail()).or().eq("phone", sysUser.getPhone());
        return userMapper.selectCount(wrapper) != 0;
    }
}
