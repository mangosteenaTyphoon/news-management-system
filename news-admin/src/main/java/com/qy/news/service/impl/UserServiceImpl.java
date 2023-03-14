package com.qy.news.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.news.constant.AuthConst;
import com.qy.news.constant.UserConst;
import com.qy.news.entity.User;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.entity.dto.UserIdReqDTO;
import com.qy.news.entity.dto.UserQueryDTO;
import com.qy.news.entity.dto.UserStatusDTO;
import com.qy.news.mapper.UserMapper;
import com.qy.news.result.R;
import com.qy.news.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.news.utils.JbcryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer type = reqDTO.getType();
        String account = reqDTO.getAccount();
        String password = reqDTO.getPassword();
        User user = userMapper.selectUserAuthInfo(account,type);
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

//    这里-1 代表注册失败 0代表有信息输入不全 1代表有账号、邮箱、手机号码重复 2 代表成功
    @Override
    public int saveUser(User user) {
        if(StrUtil.isEmpty(user.getUserName()) || StrUtil.isEmpty(user.getPhone())
          || StrUtil.isEmpty(user.getNickName() )||StrUtil.isEmpty(user.getEmail())
          || StrUtil.isEmpty(user.getPassword()) ) return 0;
        if(checkUserByUserNameAndEmailAndPhone(user)) return 1;
        user.setPassword(JbcryptUtil.bcryptPwd(user.getPassword()));
        user.setId(IdUtil.simpleUUID());
        user.setStatus(UserConst.USER_STATUS_NORMAL);
        return 0 < userMapper.insert(user) ? 2 : -1;
    }

    @Override
    public boolean updateUserStatus(UserStatusDTO reqDTO) {
        if (reqDTO.getId() != null) {
            User user = new User();
            user.setId(reqDTO.getId());
            user.setStatus(reqDTO.getStatus());
            return userMapper.updateById(user) == 1;
        }
        return false;
    }

    @Override
    public R queryUserListByWordAndPage(UserQueryDTO reqDTO, Long current, Long limit) {
        //防止为空
        if ( current == null ) current = 1L;
        if ( limit == null) limit = 10L;
        Page<User> userPage = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(reqDTO.getUserName())) {
            wrapper.like("user_name", reqDTO.getUserName());
        }
        if (!StrUtil.isEmpty(reqDTO.getNickName())) {
            wrapper.like("nick_name", reqDTO.getNickName());
        }
        if(!StrUtil.isEmpty(reqDTO.getStartDate())){
            wrapper.ge("gmt_create", reqDTO.getStartDate());
        }
        if ( !StrUtil.isEmpty(reqDTO.getEndDate()) ){
            wrapper.le("gmt_create", reqDTO.getEndDate());
        }
        //这里拼接上 不等于删除用户
        wrapper.ne("status", UserConst.USER_STATUS_DEL);
        wrapper.orderByDesc("gmt_create");
        //调用方法
        userMapper.selectPage(userPage, wrapper);
        List<User> userList = userPage.getRecords();
        if (userList != null && userList.size() > 0) {
            for (User user: userList) {
                user.setPassword(null);
            }
        }

        long total = userPage.getTotal();
        return R.ok().data("data", userList).data("total", total);

    }

    @Override
    public boolean deleteUser(UserIdReqDTO reqDTO) {
        if( StrUtil.isEmpty(reqDTO.getId()) ) return false;
        User user = new User();
        user.setId(reqDTO.getId());
        user.setStatus(UserConst.USER_STATUS_DEL);
        return userMapper.updateById(user) == 1;
    }


    /*
 * 根据用户账号、邮箱、电话进行查重
 */
    public boolean checkUserByUserNameAndEmailAndPhone(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName()).or().eq("email", user.getEmail()).or().eq("phone", user.getPhone());
        return userMapper.selectCount(wrapper) != 0;
    }
}
