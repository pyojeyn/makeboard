package com.test.demo.controller;

import com.test.demo.service.UserService;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
class RestController {

    // 생성자 주입
    @Autowired
    private final UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public void insertMember(User user){
        userService.insertUser(user);
        System.out.println("회원가입 완");
    }
}
