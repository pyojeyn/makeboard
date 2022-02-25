package com.test.demo.controller;

import com.test.demo.service.BoardService;
import com.test.demo.service.BoardServiceImpl;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.Board;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BoardApiController {

    @Autowired // @Service 달아줘야함.
    private final BoardServiceImpl boardService;

    @Autowired
    private final UserServiceImpl userService;

    public BoardApiController(BoardServiceImpl boardService, UserServiceImpl userService) { this.boardService = boardService;
    this.userService=userService;}

    @RequestMapping(value = "/writeboard", method = RequestMethod.GET)
    public ModelAndView writeboard(HttpSession httpSession) throws Exception{
        ModelAndView mav = new ModelAndView("writeboard");

        System.out.println("1");
        User user = (User) httpSession.getAttribute("member");
        int id = user.getId(); // ==> 0

        user = userService.selectOne(id);
        System.out.println("user" + user);
        mav.addObject("member",user);
        System.out.println("3");

        return mav;
    }

    @RequestMapping(value = "/createpost", method = RequestMethod.POST)
    public Map<String, Object> createpost(@RequestBody Map<String,Object> params) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        Board board = new Board();

        board.setWriter(String.valueOf(params.get("writer")));
        board.setTitle(String.valueOf(params.get("title")));
        board.setContent(String.valueOf(params.get("content")));

        int result = boardService.uploadBoard(board);

        if(result > 0){
          resultMap.put("msg","success");
        }

        return resultMap;
    }




}
