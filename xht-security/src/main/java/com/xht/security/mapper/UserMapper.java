package com.xht.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xht.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getByName(@Param("username") String username);
}
