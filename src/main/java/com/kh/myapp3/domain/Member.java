package com.kh.myapp3.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Member {
  private Long memberId; //  MEMBER_ID	NUMBER(8,0)	No
  private String email; //  EMAIL	VARCHAR2(40 BYTE)	Yes
  private String pw; //  PW	VARCHAR2(10 BYTE)	Yes
  private String nickname; //	VARCHAR2(30 BYTE)	Yes
  private LocalDateTime cdate; // CDATE	TIMESTAMP(6)	No
  private LocalDateTime udate; //  UDATE	TIMESTAMP(6)	No

  public Member(String email, String pw, String nickname) {
    this.email = email;
    this.pw = pw;
    this.nickname = nickname;
  }

}