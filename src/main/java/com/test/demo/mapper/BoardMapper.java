package com.test.demo.mapper;

import com.test.demo.vo.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 게시판 upload
    int uploadBoard(Board board) throws Exception;

    // 게시판 list
    List<Board> allBoard() throws Exception;

    Board boardDetail(int id) throws Exception;

    List<Board> myboardList(String userId) throws Exception;

    int updateBoard(Board board) throws Exception;

}
