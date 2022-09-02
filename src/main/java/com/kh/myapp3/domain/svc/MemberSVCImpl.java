package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Member;
import com.kh.myapp3.domain.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;
  /**
   * 등록
   *
   * @param member 신규 회원정보
   * @return 저장할 회원정보
   */
  @Override
  public Member insert(Member member) {

    Long generatedMemberId = memberDAO.generateMemberid();
    member.setMemberId(generatedMemberId);
    memberDAO.insert(member);
    return memberDAO.findById(generatedMemberId);
  }

  /**
   * 수정
   *
   * @param memberId 회원아이디
   * @param member   수정할 정보
   */
  @Override
  public void update(Long memberId, Member member) {

    int cnt = memberDAO.update(memberId, member);
    log.info("수정건수={},", cnt);
  }

  /**
   * 조회
   *
   * @param memberId 회원아이디
   * @return 회원정보
   */
  @Override
  public Member findById(Long memberId) {
    return memberDAO.findById(memberId);
  }

  /**
   * 탈퇴
   *
   * @param memberId 회원아이디
   */
  @Override
  public int del(Long memberId) {
    return memberDAO.del(memberId);
  }

  /**
   * 목록
   *
   * @return 회원 전체 목록
   */
  @Override
  public List<Member> all() {
    return null;
  }
}
