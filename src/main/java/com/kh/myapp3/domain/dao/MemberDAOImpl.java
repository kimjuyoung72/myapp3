package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO{

  private final JdbcTemplate jt;

  /**
   * 신규 회원아이디(내부관리용) 생성
   *
   * @return 회원아이디
   */
  @Override
  public Long generateMemberid() {
    String sql = "select member_member_id_seq.nextval from dual ";
    Long memberId = jt.queryForObject(sql, Long.class);


    return memberId;
  }

  /**
   * 가입
   *
   * @param member 가입 정보
   * @return 가입건수
   */
  @Override
  public int insert(Member member) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member(member_id, email, pw, nickname) ");
    sql.append("values (?, ?, ?, ?) ");

//    Long memberId = generateMemberid();
    result = jt.update(sql.toString(), member.getMemberId(), member.getEmail(), member.getPw(), member.getNickname());

    return result;
  }

  /**
   * 수정
   *
   * @param memberId 회원아이디
   * @param member   수정할 정보
   */
  @Override
  public int update(Long memberId, Member member) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(" update member ");
    sql.append("    set pw = ?, ");
    sql.append("        nickname = ?, ");
    sql.append("        udate = systimestamp ");
    sql.append("  where member_id = ? ");
    sql.append("    and pw = ? ");

    result = jt.update(sql.toString(), member.getNickname(), memberId, member.getPw());

    return result;
  }

  /**
   * 조회
   *
   * @param memberId 회원아이디
   * @return 회원정보
   */
  @Override
  public Member findById(Long memberId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id, email, pw, nickname, cdate, udate ");
    sql.append("from member ");
    sql.append("where member_id = ? ");

    Member findedMember = null;
    try {
//      BeanPropertyRowMapper는 매핑되는 자바클래스에 디플토 생성자 필수!
      findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memberId);
    } catch (DataAccessException e) {
      log.info("찾고자하는 회원이 없습니다.{}", memberId);
    }


    return findedMember;
  }

  /**
   * 탈퇴
   *
   * @param memberId 회원아이디
   */
  @Override
  public int del(Long memberId, String pw) {
    int result = 0;

    String sql = "delete from member where member_id = ? and pw = ? ";

    result = jt.update(sql.toString(), memberId, pw);
    return result;
  }

  /**
   * 목록
   *
   * @return 회원 전체 목록
   */
  @Override
  public List<Member> all() {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id, email, pw, nickname ");
    sql.append("  from member ");

    return jt.query(sql.toString(), new BeanPropertyRowMapper<>(Member.class));
  }

  /**
   * 전체삭제
   */
  @Override
  public void deleteAll() {

  }




}
