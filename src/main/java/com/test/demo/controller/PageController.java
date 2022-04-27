package com.test.demo.controller;

import com.test.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    private final UserServiceImpl userService;

    @Autowired // 생성자 주입
    public PageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String main() { return "main";}

    @GetMapping("join")
    public String join() {
        return "join";
    }

    @GetMapping("login")
    public String login() { return "login";}

}
