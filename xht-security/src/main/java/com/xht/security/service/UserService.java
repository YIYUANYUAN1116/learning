package com.xht.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xht.security.entity.User;

public interface UserService extends IService<User> {
    User getByName(String username);
}
