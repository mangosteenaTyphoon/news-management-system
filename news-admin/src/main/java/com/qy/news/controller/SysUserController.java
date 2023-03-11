package com.qy.news.controller;


import com.news.core.result.R;
import com.qy.news.entity.SysUser;
import com.qy.news.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "用户管理")
@RestController
@RequestMapping("/news/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

//    @ApiOperation("添加用户")
//    @PostMapping("addUser")
//    public R saveOrupdateUser(@RequestBody SysUser sysUser){
//            if(StringUtils.isEmpty(sysUser.toString())){
//                return R.error().message("未输入有效信息");
//            }
//            return R.ok().success();
//    }

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
        return R.ok().data("items", sysUserService.list(null));
    }

}

