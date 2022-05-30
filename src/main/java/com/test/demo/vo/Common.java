package com.test.demo.vo;

import com.test.demo.paging.Criteria;
import com.test.demo.paging.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Common extends Criteria {
    /** 페이징 정보 */
    private PaginationInfo paginationInfo;

    /** 삭제 여부 */
    private String deleteYn;

    /** 등록일 */
    private LocalDateTime insertTime;

    /** 수정일 */
    private LocalDateTime updateTime;

    /** 삭제일 */
    private LocalDateTime deleteTime;
}
