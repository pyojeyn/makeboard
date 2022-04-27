package com.test.demo.controller;

import com.test.demo.service.BoardServiceImpl;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
    RestController 클래스를 만들고 어노테이션을 @RestController 로 입력.
    그러나 빨간줄로 restController is not an annotation type 오류가 나며 해당 오류 내용이 나오며 import 로는 불가능하고 @org.springFramework.~~ 형태로 어노테이션을 작성해야 했다.
    뭔가 이상해서 구글링을 해보니 어노테이션 안의 RestController 클래스와 충돌이 발생해서 그렇다는 내용 확인.
    그래서 Class 이름을 RestApiController 로 변경 후 에러 잡음.
*/
@RestController
class UserApiController {

    // 로그하기 위해서 , 팩토리 패턴
    private static Logger logger = LoggerFactory.getLogger(UserApiController.class);


    private final UserServiceImpl userService;
    private final BoardServiceImpl boardService;

    // 생성자 주입
    @Autowired
    public UserApiController(UserServiceImpl userService, BoardServiceImpl boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    // 전체 회원 리스트 조회
    @RequestMapping(value = "/selectUserList", method = RequestMethod.GET)
    public ModelAndView selectUserList(Model model) throws Exception{
        ModelAndView mav = new ModelAndView("listall");
        List<User> allUser = userService.selectUserList();

        logger.info("allUser={}", allUser);
        logger.info("allUser.size()={}", allUser.size());

        // 파라미터에는 Model을 받았는데.. 페이지는 ModelAndView 에 던져주고.. 데이터는 그냥 Model 에 이렇게 따로따로 담아주는것이.. 과연 좋은 코드일까. 0427
        // mav으로 하든 model로 하든 둘 다 상관 없는듯?
        // mav.addObject("allUser",allUser);
        model.addAttribute("allUser",allUser);
        return mav;
    }

    // 회원가입
    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public Map<String, Object> insertMember(@RequestBody Map<String, Object> params, HttpSession session) throws Exception{
//        ModelAndView mav = new ModelAndView("main"); // 반환할 페이지 생성자에 담아서 객체로 만들기
//        System.out.println("insertMember.회원가입userId : " + user.getUserId());
//        System.out.println("insertMember.회원가입id : " + user.getId());

        Map<String, Object> resultMap = new HashMap<>();

        String userid = String.valueOf(params.get("user_id"));

        int checkid = userService.checkId(userid);

        System.out.println(params);
        System.out.println(String.valueOf(params.get("user_id"))); //{}

        System.out.println(checkid); // 1

        if(checkid > 0){
            resultMap.put("nounique","중복된아이디");
        }else{
            User user = new User();
            user.setUserId(String.valueOf(params.get("user_id")));
            user.setUserPw(String.valueOf(params.get("user_pw")));
            user.setUserNkname(String.valueOf(params.get("user_nkname")));
            userService.insertUser(user);
            // insert 할때는 자동생성키는 필드에 바로 안담긴다. 그래서 UserId를 매개변수로 회원가입 된 User 객체를 찾아와서 mav.addObject 에 넣어준다.
            // 그래야 회원가입하고 main 페이지로 갈때 userId의 값을 담아서 화면에 출력해줄 수 있다.
            User checkUser = userService.checkUser(user.getUserId());

            // 세션 담아주기.
            session.setAttribute("member", checkUser);
            session.setAttribute("userId", checkUser.getUserId());
            session.setAttribute("userPw", checkUser.getUserPw());
            session.setAttribute("userNkname", checkUser.getUserNkname());

            resultMap.put("done", "회원가입 성공임");

        }
        return resultMap;
    }

    // 로그인후 처리 실행만? API 완전 완전 어려움.
    @RequestMapping(value = "/loginExecute", method = RequestMethod.POST)  //어떻게 Map 으로 받는거지?
    public Map<String, Object> loginExecute(@RequestBody Map<String, Object> params, HttpSession session){

        // Map 으로 프론트한테 보내줌
        Map<String, Object> resultMap = new HashMap<>();

        User checkUser = new User(); // 1. 빈 User 객체 하나 만들기
        logger.info("params={}",params.toString()); // 넘어온 값들 출력

        // 빈 User 객체에 넘어온 값들을 UserId, UserPw 필드에 set 해줌 하지만 params 의 타입은 Object 이기 때문에 형변환을 해줘야 한다.
        checkUser.setUserId(String.valueOf(params.get("user_id"))); // "" 안에 들어가는 거는 axios 에서 보낸 프로퍼티랑 똑같이 입력해줘야 한다.
        checkUser.setUserPw(String.valueOf(params.get("user_pw")));

        // try~catch 구문은 최대한 짧아야한다! Exception 이 나지 않을 코드는 try 구문 안에 넣지 말자!
        try{
            // 로그인 수행 ( 유저 체크 )
            User loginUser = userService.login(checkUser);

            if(loginUser == null) { // 로그인 실패
                resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 프로트단에 보낼 메세지
                resultMap.put("msg", "로그인 실패했습니다.");
                resultMap.put("log", "1");
            } else {    //로그인 성공 mainpage 에서 session 값 받아서 값 출력
                session.setAttribute("member", loginUser);
                session.setAttribute("userId", loginUser.getUserId());
                session.setAttribute("userPw", loginUser.getUserPw());

                resultMap.put("code", HttpStatus.OK.value());
                resultMap.put("log", "2");
            }

        } catch (Exception e){
            logger.info("로그인 오류 : " + e.getMessage());
            resultMap.put("msg", e.getMessage());
            // 이거 catch 밖에다 했는데 로그인안되고 3으로 넘어가네;
            resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultMap.put("log", "3");

        }



        return resultMap;
    }



    // 회원하나 조회해서 관련 데이터 뿌려줘야함?   @pathViriable
    @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
    public ModelAndView selectOne(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("myprofile");

        User user = (User) session.getAttribute("member"); // 세션에 있는 객체 받아와서 user 객체에 할당.
        int id = user.getId(); // ==> 아이디를 int id 에 할당해주고
        user = userService.selectOne(id); // 그 아이디를 selectOne의 매개변수로

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


        @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
        public ModelAndView updateMember(HttpSession session,User user2) throws Exception{
            ModelAndView mav = new ModelAndView("main");

            session.setAttribute("member", user2);
            session.setAttribute("userId", user2.getUserId());
            session.setAttribute("userPw", user2.getUserPw());
            session.setAttribute("userNkname", user2.getUserNkname());

            System.out.println("새로 바꾼 닉네임 : " + user2.getUserNkname());

            userService.updateUser(user2);

            return mav;
        }

        @RequestMapping(value = "/deleteMember/{id}", method = RequestMethod.DELETE)
        public ModelAndView deleteMember(HttpSession session, @PathVariable int id) throws Exception{
            ModelAndView mav = new ModelAndView("main");

            // id는 회원아이디..
            User user = userService.selectOne(id);
            System.out.println("userService.selectOne : " + user);

            String writer = user.getUserId();
            System.out.println("writer = " + writer);



            // 해당 회원이 남긴 글 삭제!
            int result = boardService.deleteBoardWithWriter(writer);

            if(result > 0){
                System.out.println("탈퇴할때 id : " + id);
                session.invalidate(); // 로그아웃 처리
                System.out.println("탈퇴할 회원이 남긴 글 삭제 성공!");
                // 유저 삭제 !!
                userService.deleteUser(id);
                System.out.println("회원탈퇴 성공~!");
            }
            return mav;
        }


        // 아이디 중복 확인
        @RequestMapping(value = "/checkId", method = RequestMethod.POST)
        public Map<String, Object> checkId(@RequestBody Map<String, Object> params) throws Exception{
            //int result = userService.checkId(user); // 이거를 mav Object에다 전달할지.. return 값으로 전달해야 될지 모르겟다.
            //System.out.println("userid : " + user); // {"user_id":"java"}

            // Map으로 프론트한테 보내줌
            Map<String, Object> resultMap = new HashMap<>();

            String userid = String.valueOf(params.get("user_id"));
            System.out.println("userid : " + userid);
            int result = userService.checkId(userid);

            if(result > 0){
                resultMap.put("msg", "중복된 아이디 입니다.");
            }else{
                resultMap.put("msg", null);
            }

            System.out.println("checkId result : "+ result );
            return resultMap;
        }


    }



