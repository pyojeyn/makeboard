package com.test.demo.controller;

import com.test.demo.service.UserService;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/*
* RestController 클래스를 만들고 어노테이션을 @RestController로 입력. 그러나 빨간줄로 restcontroller is not an annotation type 오류가 나며 해당 오류 내용이 나오며 import로는 불가능하고 @org.springFramework.~~ 형태로 어노테이션을 작성해야 했다.

뭔가 이상해서 구글링을 해보니 어노테이션 안의 RestController 클래스와 충돌이 발생해서 그렇다는 내용 확인.

그래서 Class이름을 RestApiController로 변경 후 에러 잡음.

* */
@RestController
class RestApiController {

    // 생성자 주입
    @Autowired
    private final UserServiceImpl userService;

    public RestApiController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public int insertMember(User user) throws Exception{
        System.out.println("userId : " + user.getUserId());
        System.out.println("id : " + user.getId());

       return userService.insertUser(user); // 왜 값을 못받아오니..
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(User user, HttpSession session, Model model) throws Exception {

        ModelAndView mav = new ModelAndView("main");

        User login = userService.login(user);

        if (login == null) {
            session.setAttribute("member", null);
            System.out.println("로그인 실패");
        } else {
            System.out.println(user.getId());
            session.setAttribute("member", login);
//            model.addAttribute("member",login);
//            mav.addObject("member",login);

            mav.addObject("userId", login.getUserId());
            mav.addObject("userNkname", login.getUserNkname());

            session.setAttribute("id",login.getId());
            session.setAttribute("userId", login.getUserId());
            session.setAttribute("userNkname", login.getUserNkname());

            System.out.println("로그인 성공");
            System.out.println(login);
        }

            return mav;
        }

        // 회원하나 조회해서 관련 데이터 뿌려줘야함?
        @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
        public ModelAndView selectOne(HttpSession session) throws Exception {
            ModelAndView mav = new ModelAndView("myprofile");

            User user = (User) session.getAttribute("member");
            //int id = user.getId();

           // System.out.println("세션으로 얻은 아이디값 : " + id); // Ok

            //user = userService.selectOne(id);
            mav.addObject("id",user.getId());
            mav.addObject("userId", user.getUserId());
            mav.addObject("userNkname", user.getUserNkname());




            return mav;

        }

        @RequestMapping(value = "/logout")
        public ModelAndView logout(HttpSession session) {
            session.invalidate();
            ModelAndView mv = new ModelAndView("redirect:/");
            System.out.println("로그아웃됨!");
            return mv;
        }

        // 정보수정.. 일단 하나 select 한 다음에 하자.
        @RequestMapping(value = "/updateMember")
        public ModelAndView udpateMember(HttpSession session,User user2) throws Exception{
            ModelAndView mav = new ModelAndView("myprofile");

            User user = (User) session.getAttribute("member");
            int id = user.getId();

            System.out.println("새로 바꾼 닉네임 : " + user2.getUserNkname());

            userService.updateUser(user2);
            System.out.println("userId : " + user.getUserId());

            return mav;// 왜 값을 못받아오니..
        }


    }



