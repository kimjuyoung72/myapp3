package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Member;

public interface MemberDAO {

  /**
   * 등록
   * @param member 회원정보
   * @return 회원아이디
   */
  int insert(Member member);

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
   * @param pw 비밀번호
   */
  int del(Long memberId, String pw);

//  /**
//   * 목록
//   * @return 회원 전체 목록
//   */
//  List<Member> all();


  /**
   * 신규 회원아이디(내부관리용) 생성
   * @return 회원아이디
   */
  Long generateMemberid();
}
