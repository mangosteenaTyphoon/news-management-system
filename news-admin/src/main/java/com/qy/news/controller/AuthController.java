package com.qy.news.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.qy.news.constant.AuthConst;
import com.qy.news.entity.dto.UserAuthInfoReqDTO;
import com.qy.news.result.R;
import com.qy.news.result.ResultCode;
import com.qy.news.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("auth")
@Api(tags = {"认证管理"}, description = "认证管理")
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
        if(StrUtil.isEmpty(reqDTO.getAccount())||StrUtil.isEmpty(reqDTO.getPassword())){
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



}
