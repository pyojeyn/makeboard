package com.test.demo.mapper;

import com.test.demo.vo.BoardFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardFileMapper {
    public int insertAttach(List<BoardFile> attachList);

    public BoardFile selectAttachDetail(Long idx);

    public int deleteAttach(Long boardIdx);

    public List<BoardFile> selectAttachList(Long boardIdx);

    public int selectAttachTotalCount(Long boardId);
}
