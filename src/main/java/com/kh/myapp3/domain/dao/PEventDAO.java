package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.PEvent;

import java.util.List;

public interface PEventDAO {

    /**
     * 등록
     * @param pEvent 공연정보
     * @return 등록된 공연
     */
    PEvent save(PEvent pEvent);


    /**
     * 수정
     * @param pEvent 수정할 공연정보
     */
    void update(Long productId, PEvent pEvent);

    /**
     * 조회
     * @param mt20id 공연 정보 아이디
     * @return 공연상세
     */
    PEvent findById(Long mt20id);

    /**
     * 삭제
     * @param mt20id 공연 정보 아이디
     */
    void delete(Long mt20id);

    /**
     * 목록
     * @return 공연 목록
     */
    List<PEvent> findAll();


}
