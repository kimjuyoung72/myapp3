package com.kh.myapp3.web.form.member;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class MemberForm {

  private Long memberId;

  private String email;
  private String pw;
  private String nickname;
  private LocalDateTime cdate;  //가입일
  private LocalDateTime udate;  //수정일

}
