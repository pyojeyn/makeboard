package com.test.demo.mapper;

import com.test.demo.paging.Criteria;
import com.test.demo.vo.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 게시판 upload
    int uploadBoard(Board board) throws Exception;

    // 게시판 list
    List<Board> allBoard(Board params) throws Exception;

    Board boardDetail(int id) throws Exception;

    List<Board> myboardList(String userId) throws Exception;

    int updateBoard(Board board) throws Exception;

    int deleteBoard(int id) throws Exception;

    // writer 를 이용해서 글 삭제
    int deleteBoardWithWriter(String writer) throws Exception;

    int selectBoardTotalCount(Board params) throws Exception;

}
