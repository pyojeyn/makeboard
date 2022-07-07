package com.test.demo.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PaginationInfo {

    /** 페이징 계산에 필요한 파라미터들이 담긴 클래스 */
    private Criteria criteria;

    /** 전체 데이터 개수 */
    private int totalRecordCount;

    /**  전체 페이지 개수 */
    private int totalPageCount;

    /** 페이지 리스트의 첫 페이지 번호 */
    private int firstPage;

    /** 페이지 리스트의 마지막 페이지 번호 */
    private int lastPage;

    /** SQL 의 조건절에 사용되는 첫 ROWNUM */
    private int firstRecordIndex;

    /** SQL 의 조건절에 사용되는 마지막 ROWNUM */
    private int lastRecordIndex;

    /** 이전 페이지의 존재 여부 */
    private boolean hasPreviousPage;

    /** 다음 페이지의 존재 여부 */
    private boolean hasNextPage;

    public PaginationInfo(Criteria criteria){
        if(criteria.getCurrentPageNo() < 1){
            criteria.setCurrentPageNo(1);
        }
        if(criteria.getRecordsPerPage() <1 || criteria.getRecordsPerPage() > 100){
            criteria.setRecordsPerPage(10);
        }
        if(criteria.getPageSize() < 5 || criteria.getPageSize() > 20){
            criteria.setPageSize(10);
        }

        this.criteria = criteria;
    }

    public void setTotalRecordCount(int totalRecordCount){
        this.totalRecordCount = totalRecordCount;

        if(totalRecordCount > 0) {
            calculation();
        }
    }

    private void calculation() {

        /* 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장) */
        totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1;
        if (criteria.getCurrentPageNo() > totalPageCount) {
            criteria.setCurrentPageNo(totalPageCount);
        }

        /* 페이지 리스트의 첫 페이지 번호 */
        firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;
                 /* ( (현재 페이지 번호 - 1) / 화면 하단의 페이지 개수 ) * 화면 하단의 페이지 개수 + 1 */
        /* 현재 페이지 번호가 15, 화면 하단의 페이지 개수가 10 이라고 가정*/
        log.info("첫번째 페이지={}", 14 / 10 * 10 + 1); // 아무리 계산해봐도 15인데 왜 11이죠


        /* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장) */
        lastPage = firstPage + criteria.getPageSize() - 1;
        if (lastPage > totalPageCount) {
            lastPage = totalPageCount;
        }

        /* SQL의 조건절에 사용되는 첫 RNUM */
        firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();

        /* SQL의 조건절에 사용되는 마지막 RNUM  오라클 DB 에서 사용 , 현재 사용 X*/
        lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();

        /* 이전 페이지 존재 여부 */
        hasPreviousPage = firstPage != 1;

        /* 다음 페이지 존재 여부 */
        hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;
    }

}
