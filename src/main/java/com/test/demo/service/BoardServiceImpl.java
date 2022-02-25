package com.test.demo.service;

import com.test.demo.mapper.BoardMapper;
import com.test.demo.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) { this.boardMapper = boardMapper;}

    @Override
    public int uploadBoard(Board board) throws Exception {
        return boardMapper.uploadBoard(board);
    }
}
