package com.qy.news.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.qy.news.constant.AuthConst;
import com.qy.news.entity.SysUser;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.entity.dto.UserIdReqDTO;
import com.qy.news.result.R;
import com.qy.news.result.ResultCode;
import com.qy.news.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("auth")
@Api(tags = {"认证管理"}, description = "拥有认证、鉴权等")
public class AuthController {

    @Autowired
    private SysUserService userService;


    /*
    * @Author 山竹
    * @Description //TODO 用户登录
    * @Date 21:37 2023/3/12
    * @Param : reqDTO
    * @return com.qy.news.result.R
    **/
    @PostMapping("/login")
    @ApiOperation("登录")
    public R login(@RequestBody UserAuthInfoReqDTO reqDTO){
        if(StrUtil.isEmpty(reqDTO.getAccount())||StrUtil.isEmpty(reqDTO.getPassword())|| reqDTO.getType() == null){
            return R.error().code(ResultCode.ILLEGAL_PARAMETER_ERROR.getCode()).message(ResultCode.ILLEGAL_PARAMETER_ERROR.getMsg());
        }
        Map<String, Object> resultMap = userService.loginHandle(reqDTO);
        String flag = resultMap.get(AuthConst.FLAG).toString();
        if (AuthConst.FLAG_ZERO_VAL.equals(flag)) {
            return R.error().code(ResultCode.USER_NO_EXIST_ERROR.getCode()).message(ResultCode.USER_NO_EXIST_ERROR.getMsg());
        }
        if (AuthConst.FLAG_ONE_VAL.equals(flag)) {
            return R.error().code(ResultCode.USER_OR_PASSWD_ERROR.getCode()).message(ResultCode.USER_OR_PASSWD_ERROR.getMsg());
        }
        if(AuthConst.FLAG_TWO_VAL.equals(flag)){
            String ID = resultMap.get(AuthConst.ID).toString();
            StpUtil.login(ID);
            return R.ok().message("登录成功").data(resultMap);
        }
        return R.ok().message("登录成功");

    }

    /**
     * @Author 山竹
     * @Description //TODO SaveOrUpdateUser
     * @Date 15:25 2023/3/13
     * @Param : SysUser reqDTO
     * @return
     **/
    @PostMapping("save")
    @ApiOperation("用户注册")
    public R SaveOrUpdateUser(@RequestBody SysUser reqDTO){
        switch (userService.saveUser(reqDTO)){
            case -1 : return R.error().message("注册异常");
            case  0 : return R.error().message("注册信息未填全");
            case  1 : return R.error().message("账号或者手机号码或邮箱重复");
            case  2 : return R.ok().message("注册成功");
        }
        return R.ok().message("注册失败");
    }

    /*
    * @Author 山竹
    * @Description //TODO 登录状态
    * @Date 10:18 2023/3/13
    * @Param : null
    * @return
    **/
    @PostMapping("/isLogin")
    @ApiOperation("登录状态")
    @SaCheckLogin
    public R isLogin(){

        return R.ok().data("isLogin",StpUtil.isLogin());
    }

    /*
    * @Author 山竹
    * @Description //TODO 获取token信息
    * @Date 10:31 2023/3/13
    * @Param : null
    * @return
    **/
    @GetMapping("/getTokenInfo")
    @ApiOperation("获取token信息")
    public R getTokenInfo() {
        return R.ok().data("token",StpUtil.getTokenInfo());
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 退出
    * @Date 10:33 2023/3/13
    * @Param : reqDTO
    * @return
    **/
    @PostMapping("/logout")
    @ApiOperation("退出")
    public R logout(@RequestBody UserIdReqDTO reqDTO) {
        StpUtil.logout(reqDTO.getId());
        return R.ok().message("注销成功~");
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 获取用户对应的角色
    * @Date 10:37 2023/3/13
    * @Param : UserIdReqDTO reqDTO
    * @return
    **/
    @PostMapping ("/getUserRole")
    @ApiOperation("获取用户对应的角色")
    public R getUserRole(@RequestBody UserIdReqDTO reqDTO) {
        return R.ok().data("userUserVo", userService.queryUserIdByRole(reqDTO));
    }









}
