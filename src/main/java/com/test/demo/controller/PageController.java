package com.test.demo.controller;

import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PageController {
    @Autowired
    private final UserServiceImpl userService;

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

//    @GetMapping("login")
//    public ModelAndView login(User user) throws Exception {
//        User login = userService.login(user);
//        ModelAndView mav = new ModelAndView("login");
//        mav.addObject("msg",null);
//        if(login == null){
//            System.out.println("로그인 실패");
//            mav.addObject("msg","아이디 또는 비밀번호 오류입니다.");
//
//        }else{
//
//        }



//        return mav;
//    }

//    @GetMapping("myprofile")
//    public String myprofile() { return "myprofile";}

//    @GetMapping("seeprofile")
//    public String seeprofile(){
//        return "seeprofile";
//    }



}
