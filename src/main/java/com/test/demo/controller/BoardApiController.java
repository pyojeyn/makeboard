package com.test.demo.controller;

import com.test.demo.service.BoardService;
import com.test.demo.service.BoardServiceImpl;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.Board;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardApiController {

    @Autowired // @Service 달아줘야함.
    private final BoardServiceImpl boardService;

    @Autowired
    private final UserServiceImpl userService;

    public BoardApiController(BoardServiceImpl boardService, UserServiceImpl userService) { this.boardService = boardService;
    this.userService=userService;}

    @RequestMapping(value = "/allBoard", method = RequestMethod.GET)
    public ModelAndView allBoard(Model model) throws Exception{
        ModelAndView mav = new ModelAndView("allBoard");
        List<Board> allBoard = boardService.allBoard();
        System.out.println("boardService.allBoard()" + allBoard);

        model.addAttribute("allBoard",allBoard);

        return mav;
    }


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

    @RequestMapping(value = "boardDetail/{id}", method = RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable int id) throws Exception{
        ModelAndView mav = new ModelAndView("boardDetail");

        System.out.println(id);
        Board boarddetail = boardService.boardDetail(id);

        mav.addObject("baord", boarddetail);
        mav.addObject("writer",boarddetail.getWriter());
        mav.addObject("id", boarddetail.getId());

        return mav;
    }

    // 내가 쓴글 List
    @RequestMapping(value = "myboardList/{userId}", method = RequestMethod.GET)
    public ModelAndView myboardList(@PathVariable String userId) throws Exception{
        ModelAndView mav = new ModelAndView("myboardList");

        System.out.println("userid :" + userId);

        List<Board> board = boardService.myboardList(userId);

        mav.addObject("myboard",board);

        return mav;
    }


    // 글 수정 페이지로 넘어가기
    @RequestMapping(value = "editboard", method = RequestMethod.GET)
    public ModelAndView editboard(@RequestParam(value = "id") int id) throws Exception{
        ModelAndView mav = new ModelAndView("editboard");
        System.out.println("PARAM : "+ id);
        Board board = boardService.boardDetail(id);

        mav.addObject("board",board);

        return mav;
    }

    // 수정..
    @RequestMapping(value = "updateBoard/{boardid}",method = RequestMethod.PUT)
    public Map<String, Object> updateBoard(@RequestBody Map<String, Object> params, @PathVariable(value = "boardid") int id) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

//        int boardid = Integer.parseInt(String.valueOf(params.get("id")));
//        System.out.println("수정!"+boardid);
        System.out.println("pathVariable" +  id);
        System.out.println("title==>" + String.valueOf(params.get("title")));

        Board board = new Board();
        board.setId(id);
        board.setTitle(String.valueOf(params.get("title")));
        board.setWriter(String.valueOf(params.get("writer")));
        board.setContent(String.valueOf(params.get("content")));
        int result = boardService.updateBoard(board);

        if(result>0){
            System.out.println("수정 성공!");
            resultMap.put("success","성공");

        }else{
            resultMap.put("fail","실패");
        }
        return resultMap;
    }




}
