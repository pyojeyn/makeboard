package com.test.demo.service;

import com.test.demo.vo.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardService {
    int uploadBoard(Board board) throws Exception;
}
