package com.qy.news.mapper;

import com.qy.news.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserAuthInfo(@Param("account") String account);


}
