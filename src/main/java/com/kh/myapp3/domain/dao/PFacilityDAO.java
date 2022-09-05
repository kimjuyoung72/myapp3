package com.kh.myapp3.domain.dao;

public interface PFacilityDAO {

  /**
   * 등록
   * @param pFacility 공연정보
   * @return 등록된 공연
   */
  PFacilityDAO save(PFacilityDAO pFacility);


  /**
   * 수정
   * @param pFacility 수정할 공연정보
   */
  void update(String mt10id, PFacilityDAO pFacility);

  /**
   * 조회
   * @param mt10id 공연 정보 아이디
   * @return 공연상세
   */
  PFacilityDAO findById(String mt10id);
}
