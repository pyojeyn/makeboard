package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping
    public String main() { return "main";}

    @GetMapping("join")
    public String join() {
        return "join";
    }

    @GetMapping("login")
    public String login() { return "login";}

//    @GetMapping("myprofile")
//    public String myprofile() { return "myprofile";}



}
