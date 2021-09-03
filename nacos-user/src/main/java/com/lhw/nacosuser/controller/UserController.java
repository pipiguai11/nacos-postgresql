package com.lhw.nacosuser.controller;

import com.lhw.nacosapi.model.User;
import com.lhw.nacosuser.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/user/info")
    public User getUserInfo(){
        return userService.getUserInfo();
    }

}
