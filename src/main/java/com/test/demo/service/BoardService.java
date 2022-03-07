package com.test.demo.service;

import com.test.demo.vo.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardService {
    List<Board> allBoard() throws Exception;
    int uploadBoard(Board board) throws Exception;
    Board boardDetail(int id) throws Exception;
    List<Board> myboardList(String userId) throws Exception;
    int updateBoard(Board board) throws Exception;
    int deleteBoard(int id) throws Exception;
    int deleteBoardWithWriter(String writer) throws Exception;
}
