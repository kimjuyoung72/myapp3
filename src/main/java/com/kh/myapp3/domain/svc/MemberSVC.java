package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Member;

import java.util.List;

public interface MemberSVC {

  /**
   * 등록
   * @param member 회원정보
   * @return 회원아이디
   */
  Member insert(Member member);

  /**
   * 수정
   * @param memberId 회원아이디
   * @param member  수정할 정보
   */
  int update(Long memberId, Member member);

  /**
   * 조회
   * @param memberId 회원아이디
   * @return 회원정보
   */
  Member findById(Long memberId);

  /**
   * 탈퇴
   * @param memberId 회원아이디
   */
  int del(Long memberId, String pw);

  /**
   * 목록
   * @return 회원 전체 목록
   */
  List<Member> all();



}
