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
import java.util.List;


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

    @RequestMapping(value = "/selectUserList", method = RequestMethod.GET)
    public ModelAndView selectUserList(Model model) throws Exception{
        ModelAndView mav = new ModelAndView("listall");
        List<User> allUser = userService.selectUserList();
        System.out.println(allUser);
        System.out.println(allUser.size());

        // mav으로 하든 model로 하든 둘 다 상관 없는듯?
        //mav.addObject("allUser",allUser);
        model.addAttribute("allUser",allUser);

        return mav;
    }

    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public ModelAndView insertMember(User user, HttpSession session) throws Exception{
        ModelAndView mav = new ModelAndView("main");
        System.out.println("insertMember.회원가입userId : " + user.getUserId());
        System.out.println("insertMember.회원가입id : " + user.getId());

        userService.insertUser(user);

        User checkUser = userService.checkUser(user.getUserId());

        session.setAttribute("member", checkUser);

//        이거때매.. 내정보에서 오류남...
        mav.addObject("id",checkUser.getId());
        mav.addObject("userId", checkUser.getUserId());
        mav.addObject("userNkname", checkUser.getUserNkname());
        return mav;// 왜 값을 못받아오니..
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

        // 회원하나 조회해서 관련 데이터 뿌려줘야함?   @pathViriable
    @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
    public ModelAndView selectOne(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("myprofile");

        User user = (User) session.getAttribute("member");
        int id = user.getId(); // ==> 0
//        String userId = user.getUserId();

        // System.out.println("세션으로 얻은 아이디값 : " + id); // Ok

//        user = userService.selectOne(userId);
        user = userService.selectOne(id);

        System.out.println("selectOne ==========>>>>> ");
        System.out.println(user);

        mav.addObject("member", user);

        return mav;

    }

    @RequestMapping(value = "/seeprofile",method = RequestMethod.GET)
    public ModelAndView seeprofile(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("seeprofile");

        User user = (User) session.getAttribute("member");
        int id = user.getId(); // ==> 0
//        String userId = user.getUserId();

        // System.out.println("세션으로 얻은 아이디값 : " + id); // Ok

//        user = userService.selectOne(userId);
        user = userService.selectOne(id);

        System.out.println("selectOne ==========>>>>> ");
        System.out.println(user);

        mav.addObject("member", user);

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
        @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
        public ModelAndView udpateMember(HttpSession session,User user2) throws Exception{
            ModelAndView mav = new ModelAndView("main");

            session.setAttribute("member", user2);

            User user = (User) session.getAttribute("member");
            int id = user.getId();

            System.out.println("새로 바꾼 닉네임 : " + user2.getUserNkname());

            userService.updateUser(user2);
            System.out.println("userId : " + user.getUserId());

            // 수정하고 값 넘겨줘야함 객체(USER) 를 넘겨줌 해당 id 값을 넘겨주고 select 를 해와서 ModelAndView 객체에 addObject 해줘야 수정하고 메인페이지로 돌아가서도 찍힘
            User selectuser = userService.selectOne(user2.getId());
            System.out.println("selectuser : " + selectuser);
//            mav.addObject("member", selectuser); // 왜 안되지 이건 왜 ?
            // 이거해줘야 수정하고 메인페이지 들어가면 userId 찍힘.
            mav.addObject("userId", selectuser.getUserId());
            mav.addObject("userNkname", selectuser.getUserNkname());

            return mav;// 왜 값을 못받아오니..
        }

        @RequestMapping(value = "/deleteMember/{id}", method = RequestMethod.DELETE)
        public ModelAndView deleteMember(HttpSession session, @PathVariable int id) throws Exception{
            ModelAndView mav = new ModelAndView("main");
            //System.out.println(user.getId());

            System.out.println("탈퇴할때 id : " + id);
            session.invalidate(); // 로그아웃 처리

            userService.deleteUser(id);

            return mav;
        }


    }



