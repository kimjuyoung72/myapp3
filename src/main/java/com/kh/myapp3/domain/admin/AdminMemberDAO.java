package com.kh.myapp3.domain.admin;

import com.kh.myapp3.domain.Member;

import java.util.List;

public interface AdminMemberDAO {


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
   *
   * */
  int del(Long memberId);

  /**
   * 목록
   * @return 회원 전체 목록
   */
  List<Member> all();

  Long generateMemberId();

}
