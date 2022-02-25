package com.test.demo.mapper;

import com.test.demo.vo.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    // 게시판 upload
    int uploadBoard(Board board) throws Exception;
}
