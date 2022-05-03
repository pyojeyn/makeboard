package com.test.demo.controller;

import com.test.demo.service.BoardService;
import com.test.demo.service.BoardServiceImpl;
import com.test.demo.service.UserServiceImpl;
import com.test.demo.vo.Board;
import com.test.demo.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BoardApiController {

    @Autowired // @Service 달아줘야함. 0411 테스트
    private final BoardServiceImpl boardService;

    @Autowired
    private final UserServiceImpl userService;

    // 생성자 주입
    public BoardApiController(BoardServiceImpl boardService, UserServiceImpl userService) { this.boardService = boardService;
    this.userService=userService;}

    /**
     *
     * @param model
     * @return
     * @throws Exception
     *
     * 게시판 리스트
     */
    @RequestMapping(value = "/allBoard", method = RequestMethod.GET)
    public ModelAndView allBoard(Model model) throws Exception{
        ModelAndView mav = new ModelAndView("allBoard");
        List<Board> allBoard = boardService.allBoard();
        log.info("boardService.allBoard()={}", allBoard);
        model.addAttribute("allBoard",allBoard);

        return mav;
    }


    /**
     *
     * @param httpSession
     * @return
     * @throws Exception
     *
     * 글쓰기
     */
    @RequestMapping(value = "/writeboard", method = RequestMethod.GET)
    public ModelAndView writeboard(HttpSession httpSession) throws Exception{
        ModelAndView mav = new ModelAndView("writeboard");

        User user = (User) httpSession.getAttribute("member");
        int id = user.getId(); // ==> 0

        user = userService.selectOne(id);
        log.info("user={}", user);
        mav.addObject("member",user);
        return mav;
    }

    /**
     *
     * @param params
     * @return
     * @throws Exception
     *
     * 글저장
     */
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

    /**
     *
     * @param id
     * @return
     * @throws Exception
     *
     * 글 상세보기
기    */
    @RequestMapping(value = "boardDetail/{id}", method = RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable int id) throws Exception{
        ModelAndView mav = new ModelAndView("boardDetail");

        log.info("board 의 아이디={}", id);
        Board boarddetail = boardService.boardDetail(id);

        mav.addObject("baord", boarddetail);
        mav.addObject("writer",boarddetail.getWriter());
        mav.addObject("id", boarddetail.getId());

        return mav;
    }

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     *
     * 내가 쓴 글 보기
     */
    @RequestMapping(value = "myboardList/{userId}", method = RequestMethod.GET)
    public ModelAndView myboardList(@PathVariable String userId) throws Exception{
        ModelAndView mav = new ModelAndView("myboardList");

        log.info("userid={}", userId);

        List<Board> board = boardService.myboardList(userId);

        mav.addObject("myboard",board);

        return mav;
    }


    /**
     *
     * @param id
     * @return
     * @throws Exception
     *
     * 수정 페이지로 넘어가기
     */
    @RequestMapping(value = "editboard", method = RequestMethod.GET)
    public ModelAndView editboard(@RequestParam(value = "id") int id) throws Exception{
        ModelAndView mav = new ModelAndView("editboard");

        log.info("PARAM={}", id);

        Board board = boardService.boardDetail(id);

        mav.addObject("board",board);

        return mav;
    }

    /**
     *
     * @param params
     * @param id
     * @return
     * @throws Exception
     *
     * 수정처리
     */
    @RequestMapping(value = "updateBoard/{boardid}",method = RequestMethod.PUT)
    public Map<String, Object> updateBoard(@RequestBody Map<String, Object> params, @PathVariable(value = "boardid") int id) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        log.info("pathVariable={}",id);
        log.info("title={}", String.valueOf(params.get("title")));

        Board board = new Board();
        board.setId(id);
        board.setTitle(String.valueOf(params.get("title")));
        board.setWriter(String.valueOf(params.get("writer")));
        board.setContent(String.valueOf(params.get("content")));
        int result = boardService.updateBoard(board);

        if(result>0){
            log.info("수정성공쓰");
            resultMap.put("success","성공");

        }else{
            resultMap.put("fail","실패");
        }
        return resultMap; // 테스트
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     *
     * 글 삭제
     */
    @RequestMapping(value = "/deleteBoard/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteBoard(@PathVariable int id) throws Exception{
        ModelAndView mav = new ModelAndView("allBoard");

        boardService.deleteBoard(id);

        return mav;
    }



}
