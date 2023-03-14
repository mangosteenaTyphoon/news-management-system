package com.qy.news.mapper;

import com.qy.news.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectUserAuthInfo(@Param("account") String account, @Param("type") Integer type);


    List<String> selectUserIdByRole(@Param("userId") String id);
}
