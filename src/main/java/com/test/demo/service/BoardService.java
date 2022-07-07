package com.test.demo.service;

import com.test.demo.paging.Criteria;
import com.test.demo.vo.Board;
import com.test.demo.vo.BoardFile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface BoardService {
    List<Board> allBoard(Board params) throws Exception;
    int uploadBoard(Board board) throws Exception;
    boolean uploadBoard(Board params, MultipartFile[] files) throws Exception;
    Board boardDetail(int id) throws Exception;
    List<Board> myboardList(String userId) throws Exception;
    int updateBoard(Board board) throws Exception;
    int deleteBoard(int id) throws Exception;
    int deleteBoardWithWriter(String writer) throws Exception;

    List<BoardFile> getAttachFileList(Long boardIdx);
}
