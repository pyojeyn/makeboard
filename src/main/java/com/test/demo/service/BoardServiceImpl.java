package com.test.demo.service;

import com.test.demo.mapper.BoardMapper;
import com.test.demo.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) { this.boardMapper = boardMapper;}

    @Override
    public List<Board> allBoard() throws Exception {
        return boardMapper.allBoard();
    }

    @Override
    public int uploadBoard(Board board) throws Exception {
        return boardMapper.uploadBoard(board);
    }

    @Override
    public Board boardDetail(int id) throws Exception {
        return boardMapper.boardDetail(id);
    }

    @Override
    public List<Board> myboardList(String userId) throws Exception {
        return boardMapper.myboardList(userId);
    }

    @Override
    public int updateBoard(Board board) throws Exception {
        return boardMapper.updateBoard(board);
    }

    @Override
    public int deleteBoard(int id) throws Exception {
        return boardMapper.deleteBoard(id);
    }

    @Override
    public int deleteBoardWithWriter(String writer) throws Exception {
        return boardMapper.deleteBoardWithWriter(writer);
    }
}
