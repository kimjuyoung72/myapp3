package com.kh.myapp3.web.admin.form.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddForm {

  @NotBlank //검증로직 not blank
//  @Email(regexp=".+@.+\\..+", message = "Email 형식이 아님.")
  @Email(regexp=".+@.+\\..+")
  private String email; //이메일
  @NotBlank
  @Length(min=8,max=10)
  private String pw;    //비밀번호
  @Size(min=0,max=30)
  private String nickname;  //별칭
}
