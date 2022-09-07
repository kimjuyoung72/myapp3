package com.kh.myapp3.web.admin.form.member;

import lombok.Data;

@Data
public class EditForm {

  private Long memberId;
  private String email;
  private String pw;
  private String nickname;
}
