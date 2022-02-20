package com.test.demo.controller;

import com.test.demo.service.UserService;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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
    public void insertMember(User user){
        User user2 = new User(); // 이거 아닌가..?
        userService.insertUser(user); // 왜 값을 못받아오니..
    }

    /*
        // 새로운 회원 삽입하기
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();

		// 회원 가입창에서 전송된 회원 정보를 bind() 메소드를 이용해 memberVO 해당 속성에 자동으로 설정
		bind(request, memberVO);

		int result = 0;
		result = memberService.addMember(memberVO);
		// 회원정보 추가 후 ModelAndView 클ㄹ스의 redirect 속성을 이용
		// /member/listMembers.do로 리다이렉트 한다
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");

		return mav;
	}

    * */
}
