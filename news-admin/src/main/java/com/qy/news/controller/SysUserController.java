package com.qy.news.controller;


import com.qy.news.entity.SysUser;
import com.qy.news.result.R;
import com.qy.news.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */

@Slf4j
@Api(tags = {"用户管理"}, description = "用户进行增删改查")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;


    /**
     * @Author 山竹
     * @Description //TODO
     * @Date 15:54 2023/3/10
     * @Param : null
     * @return
     **/
    @ApiOperation("获取全部用户")
    @GetMapping("getAllUser")
    public R getAllUser(){


        return R.ok().data("items", userService.list(null));
    }

}

