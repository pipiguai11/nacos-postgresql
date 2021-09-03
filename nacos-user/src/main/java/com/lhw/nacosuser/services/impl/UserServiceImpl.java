package com.lhw.nacosuser.services.impl;

import com.lhw.nacosapi.model.User;
import com.lhw.nacosuser.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Override
    public User getUserInfo() {
        System.out.println("调用了获取用户服务");
        User user = new User();
        user.setName("lhw");
        user.setAddress("gd");
        user.setAge(18);
        return user;
    }
}
