package com.test.demo.controller;

import com.test.demo.service.BoardServiceImpl;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;


/*
    RestController 클래스를 만들고 어노테이션을 @RestController 로 입력.
    그러나 빨간줄로 restController is not an annotation type 오류가 나며 해당 오류 내용이 나오며 import 로는 불가능하고 @org.springFramework.~~ 형태로 어노테이션을 작성해야 했다.
    뭔가 이상해서 구글링을 해보니 어노테이션 안의 RestController 클래스와 충돌이 발생해서 그렇다는 내용 확인.
    그래서 Class 이름을 RestApiController 로 변경 후 에러 잡음.
*/
@RestController
class UserApiController {

    // 후후후후후
    // 로그하기 위해서 , 팩토리 패턴
    private static Logger logger = LoggerFactory.getLogger(UserApiController.class);


    private final UserServiceImpl userService;
    private final BoardServiceImpl boardService;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 생성자 주입
    @Autowired
    public UserApiController(UserServiceImpl userService, BoardServiceImpl boardService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.boardService = boardService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 전체 회원 리스트
     * @param model
     * @return
     * @throws Exception
     */
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

    /**
     * 회원 가입
     * @param params
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public Map<String, Object> insertMember(@RequestBody Map<String, Object> params, HttpSession session) throws Exception{

        Map<String, Object> resultMap = new HashMap<>();

        String userid = String.valueOf(params.get("user_id"));

        int checkid = userService.checkId(userid); // 중복 아이디 있나 없나 체크하기 위해
        String encodedPassword = passwordEncoder.encode(String.valueOf(params.get("user_pw"))); // 비밀번호 암호화 시키기
        logger.info("암호화된 비번={}", encodedPassword);

        // 체크박스값 String 으로 형변환, "[", "]" , 공백 제거
        String userhobbies = String.valueOf(params.get("user_hobby"));
        userhobbies = userhobbies.replace("[", "").replace("]", "").replace(" ", "");

        logger.info("params={}", params);
        logger.info("params.get(user_id)={}", String.valueOf(params.get("user_id")));
        logger.info("checkid={}", checkid);

        if(checkid > 0){
            resultMap.put("nounique","중복된아이디");
        }else{
            User user = new User();
            user.setUserId(String.valueOf(params.get("user_id")));
            user.setUserPw(encodedPassword); // 여기서 암호화된 비밀번호 넣어야 함.
            user.setUserNkname(String.valueOf(params.get("user_nkname")));
            user.setUserHobby(userhobbies);

            userService.insertUser(user);

            // insert 할때는 자동생성키는 필드에 바로 안담긴다. 그래서 UserId를 매개변수로 회원가입 된 User 객체를 찾아와서 mav.addObject 에 넣어준다.
            // 그래야 회원가입하고 main 페이지로 갈때 userId의 값을 담아서 화면에 출력해줄 수 있다.
            User checkUser = userService.checkUser(user.getUserId());


            // 세션 담아주기.
            session.setAttribute("member", checkUser);
            session.setAttribute("userId", checkUser.getUserId());
            session.setAttribute("userPw", checkUser.getUserPw());
            session.setAttribute("userNkname", checkUser.getUserNkname());
            session.setAttribute("userRegdate", checkUser.getUserRegdate());
            session.setAttribute("userHobby", checkUser.getUserHobby());

            resultMap.put("done", "회원가입 성공임");

        }
        return resultMap;
    }

    /**
     * 로그인 처리
     * @param params
     * @param session
     * @return
     */
    @RequestMapping(value = "/loginExecute", method = RequestMethod.POST)  //어떻게 Map 으로 받는거지?
    public Map<String, Object> loginExecute(@RequestBody Map<String, Object> params, HttpSession session) throws Exception {

        // Map 으로 프론트한테 보내줌
        Map<String, Object> resultMap = new HashMap<>();

        User checkUser = new User(); // 1. 빈 User 객체 하나 만들기
        logger.info("params={}",params.toString()); // 넘어온 값들 출력


        int IsRightID = userService.checkId(String.valueOf(params.get("user_id")));
        logger.info("isRigthID={}", IsRightID);

        if(IsRightID < 1){
            resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 프로트단에 보낼 메세지
            resultMap.put("msg", "아이디 일치하지 않음.");
            resultMap.put("log", "1");
        }else{
            User checkPw = userService.getPw(String.valueOf(params.get("user_id")));


            // 실제 DB에 있는 비밀번호
            String encodedPw = checkPw.getUserPw();
            logger.info("DB 암호화된 비번 ={}", encodedPw);

            // 아이디 일치 여부


            // 로그인 할때 입력한 비밀번호
            String password = String.valueOf(params.get("user_pw"));
            logger.info("실제로 입력한 비밀번호={}", password);

            // 빈 User 객체에 넘어온 값들을 UserId, UserPw 필드에 set 해줌 하지만 params 의 타입은 Object 이기 때문에 형변환을 해줘야 한다.
            // "" 안에 들어가는 거는 axios 에서 보낸 프로퍼티랑 똑같이 입력해줘야 한다.
            // 진짜 비밀번호랑 입력한 비밀번호랑 일치하는지 비교!
            if(passwordEncoder.matches(password, encodedPw)){
                logger.info("비밀번호 일치함!");
                checkUser.setUserId(String.valueOf(params.get("user_id")));
                checkUser.setUserPw(encodedPw);
            }


            // try~catch 구문은 최대한 짧아야한다! Exception 이 나지 않을 코드는 try 구문 안에 넣지 말자!
            try{
                // 로그인 수행 ( 유저 체크 )
                User loginUser = userService.login(checkUser);
                logger.info("loginUser={}", loginUser);

                if(loginUser == null) { // 로그인 실패
                    resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 프로트단에 보낼 메세지
                    resultMap.put("msg", "틀린 비밀번호입니다.");
                    resultMap.put("log", "1");
                } else {    //로그인 성공 mainpage 에서 session 값 받아서 값 출력
                    session.setAttribute("member", loginUser);
                    session.setAttribute("userId", loginUser.getUserId());
                    session.setAttribute("userPw", loginUser.getUserPw());
                    session.setAttribute("userHobby", loginUser.getUserHobby());
                    session.setAttribute("userRegdate", loginUser.getUserRegdate());


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
        }


        return resultMap;
    }


    /**
     * 회원 1명 조회
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
    public ModelAndView selectOne(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("myprofile");

        // 0501 굳이 selectOne 하는 mapper 를 사용해야 하나? 그냥 세션으로 가져온 애를 그냥 mav 에 넘겨줘도 되지 않나?
        User user = (User) session.getAttribute("member"); // 세션에 있는 객체 받아와서 user 객체에 할당.
        String userHobby = user.getUserHobby();

        mav.addObject("userHobby", userHobby);
        mav.addObject("member", user);

        return mav;
    }


    @RequestMapping(value = "/seeprofile",method = RequestMethod.GET)
    public ModelAndView seeprofile(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("seeprofile");

        User user = (User) session.getAttribute("member");
        logger.info("seeprofile.selectOne={}", user);
        mav.addObject("member", user);
        mav.addObject("userRegdate", user.getUserRegdate());

        return mav;
    }


    /**
     * 로그아웃
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        //  0505 여기서 문제 있음. 로그아웃 하면 밑에 로그도 찍혀야 하고 원래 메인페이지로 돌아가야 하는데 로그도 안찍히고 이전 로그인 페이지로만 돌아감;
        // 혹시 시큐리티 때문인지...? 시큐리티랑 로그인 로직 부분 확인해봐야겠다. => 0507 해결함. 시큐리티 설정 때문 맞음 ;
        ModelAndView mv = new ModelAndView("redirect:/");
        logger.info("로그아웃됨");
        return mv;
    }


    /**
     * 회원 수정
     * @param session
     * @param user2
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
    public ModelAndView updateMember(HttpSession session,User user2) throws Exception{
        ModelAndView mav = new ModelAndView("main");

        session.setAttribute("member", user2);
        session.setAttribute("userId", user2.getUserId());
        session.setAttribute("userPw", user2.getUserPw());
        session.setAttribute("userNkname", user2.getUserNkname());

        logger.info("새로 바꾼 닉네임={}", user2.getUserNkname());
        userService.updateUser(user2);
        return mav;
    }

    /**
     * 회원 탈퇴
     * @param session
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMember/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteMember(HttpSession session, @PathVariable int id) throws Exception{
        ModelAndView mav = new ModelAndView("main");

        // id는 회원아이디
        User user = userService.selectOne(id);
        logger.info("deleteMember.selectOne={}", user);

        String writer = user.getUserId();

        logger.info("writer={}", writer);

        // 해당 회원이 남긴 글 삭제!
        int result = boardService.deleteBoardWithWriter(writer);
        if(result > 0){
            logger.info("탈퇴할 회원이 남긴 글 삭제 성공!");
        }
        // 유저 삭제 !!
        session.invalidate(); // 로그아웃 처리
        userService.deleteUser(id);
        logger.info("회원탈퇴 성공!");
        return mav;
    }


    /**
     * 아이디 중복 확인
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkId", method = RequestMethod.POST)
    public Map<String, Object> checkId(@RequestBody Map<String, Object> params) throws Exception{
        // Map 으로 프론트한테 보내줌
        Map<String, Object> resultMap = new HashMap<>();

        String userid = String.valueOf(params.get("user_id"));
        logger.info("userid={}", userid);

        int result = userService.checkId(userid);

        if(result > 0){
            resultMap.put("msg", "중복된 아이디 입니다.");
        }else{
            resultMap.put("msg", null);
        }

        logger.info("checkId result={}", result);
        return resultMap;
    }

    /**
     * 비밀번호 변경 페이지 이동
     * @param session
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ModelAndView changePassword(HttpSession session){
        ModelAndView mav = new ModelAndView("changePassword");
        User user = (User) session.getAttribute("member"); // userId를 넘겨주기 위해서
        mav.addObject("member", user);
        return mav;
    }

    /**
     * 비밀번호 변경 로직
     * @param params
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.POST)
    public Map<String, Object> changePassword(@RequestBody Map<String, Object> params, @PathVariable int userId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        // 1. userId 로 user 객체 먼저 가져옴
        User user = userService.forCheckPw(userId);

        // 2. user객체.getUserPw 와 이전비밀번호에 실제로 입력한 값이 맞는지 일치해야함. matches
        String originalPw = user.getUserPw(); // 실제 DB 에 저장되어있는 암호 ==> user객체.getUserPw
        String oldPw = String.valueOf(params.get("old_pw")); // ==> 실제로 입력한 이전 비밀번호

        if(passwordEncoder.matches(oldPw, originalPw)){
            // 변경할 비밀번호 암호화 해서 변수에 담기
            String encodedNewPw = passwordEncoder.encode(String.valueOf(params.get("new_pw")));
            // 쿼리파라미터로 들어온 id 와 새로 입력한 비밀번호 암호화 한거 매개변수로 넣어서 mapper 단까지 보내줌
            int result = userService.changePassword(userId, encodedNewPw);
            if(result > 0){
                resultMap.put("changemsg", "비밀번호가 정상적으로 변경되었습니다.");
                resultMap.put("code", HttpStatus.OK.value());
            }
        }else{
            resultMap.put("failmsg", "이전 비밀번호 일치하지 않음");
            resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return resultMap;
    }
}



