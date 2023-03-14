package com.qy.news.controller;


import com.qy.news.entity.SysUser;
import com.qy.news.entity.dto.UserQueryDTO;
import com.qy.news.entity.dto.UserStatusDTO;
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

    /*
     * @Author 山竹
     * @Description //TODO 修改用户状态
     * @Date 21:34 2023/3/13
     * @Param : UserStatusDTO reqDTO
     * @return R
     **/
    @PostMapping("/updateUserStatus")
    @ApiOperation("修改用户状态")
    public R updateUserStatus(@RequestBody UserStatusDTO reqDTO){
        return userService.updateUserStatus(reqDTO) ? R.ok():R.error();
    }

    /*
     * @Author 山竹
     * @Description //TODO 查询全部用户并且分页
     * @Date 21:57 2023/3/13
     * @Param : UserQueryDTO reqDTO
     * @Param : long Current
     * @Param : long limit
     * @return R
     **/
    @PostMapping("/queryUserList/{current}/{limit}")
    @ApiOperation("查询全部用户并且分页")
    public R queryUserListByWordAndPage(@RequestBody(required = false) UserQueryDTO reqDTO, @PathVariable(required = false) Long current,
                                        @PathVariable(required = false) Long limit) {
        return userService.queryUserListByWordAndPage(reqDTO,current,limit);
    }

    /*
    * @Author 山竹
    * @Description //TODO 修改用户信息
    * @Date 23:26 2023/3/13
    * @Param : SysUser reqDTO
    * @return R
    **/
    @PostMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public R updateUser(@RequestBody SysUser reqDTO) {
        return userService.updateById(reqDTO) ? R.ok(): R.error();
    }



}

