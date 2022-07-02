package com.test.demo.service;

import com.test.demo.mapper.BoardFileMapper;
import com.test.demo.mapper.BoardMapper;
import com.test.demo.paging.Criteria;
import com.test.demo.paging.PaginationInfo;
import com.test.demo.util.FileUtils;
import com.test.demo.vo.Board;
import com.test.demo.vo.BoardFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardMapper boardMapper;

    @Autowired
    private final FileUtils fileUtils;

    @Autowired
    private final BoardFileMapper boardFileMapper;

    public BoardServiceImpl(BoardMapper boardMapper, FileUtils fileUtils, BoardFileMapper boardFileMapper) { this.boardMapper = boardMapper; this.fileUtils = fileUtils;  this.boardFileMapper=boardFileMapper;}

    @Override
    public List<Board> allBoard(Board params) throws Exception {
        List<Board> boardList = Collections.emptyList();

        int boardTotalCount = boardMapper.selectBoardTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardTotalCount);

        params.setPaginationInfo(paginationInfo);

        if(boardTotalCount > 0){
            boardList = boardMapper.allBoard(params);
        }

        return boardList;
    }

    @Override
    public int uploadBoard(Board board) throws Exception {
        return boardMapper.uploadBoard(board);
    }

    @Override
    public boolean uploadBoard(Board params, MultipartFile[] files) throws Exception {
        int queryResult = 1;

        if(uploadBoard(params) < 1){
            return false;
        }
        //        Long boardid =  getBoardId(params.getContent()); // 0627 이렇게 아이디 가져오묜 안되는데 시간날때 다른 방법 찾아봐야함.
        log.info("param.getId={}", params.getId()); // Insert 하자마자 pk 가져오기성공!!! 내일 노션 써야지!!

        List<BoardFile> fileList = fileUtils.uploadFiles(files, params.getId());

        if(CollectionUtils.isEmpty(fileList) == false) {
            queryResult = boardFileMapper.insertAttach(fileList);
            if(queryResult < 1) {
                queryResult = 0;
            }
        }
        return (queryResult > 0);
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

    @Override
    public Long getBoardId(String content) throws Exception {
        return boardMapper.getBoardId(content);
    }
}
