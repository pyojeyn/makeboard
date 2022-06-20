package com.test.demo.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.annotation.Secured;

@Getter
@Setter
public class BoardFile extends Common{

    /** 파일 번호 (PK) */
    private Long id;

    /** 게시글 번호 (FK) */
    private Long boardId;

    /** 원본 파일명 */
    private String originalName;

    /** 저장 파일명 */
    private String saveName;

    /** 파일 크기 */
    private long size;
}
