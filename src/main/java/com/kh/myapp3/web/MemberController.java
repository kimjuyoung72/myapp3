package com.kh.myapp3.web;

import com.kh.myapp3.domain.Member;
import com.kh.myapp3.domain.svc.MemberSVC;
import com.kh.myapp3.web.form.member.AddForm;
import com.kh.myapp3.web.form.member.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;
  //가입화면
  @GetMapping("/add")
  public String add() {

    return "member/addForm";
  }

  //가입처리 post /members/add  AddFrom
  @PostMapping("/add")
  public String join(AddForm addForm) {
    //검증
    log.info("addForm:{}", addForm);  //toString()이 처리.

    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPw(addForm.getPw());
    member.setNickname(addForm.getNickname());

//    Member insert = memberSVC.insert(member);
    memberSVC.insert(member);


    return "login/loginForm"; //login 화면
  }
  //조회화면 get /members/{id} MemberForm
  @GetMapping("/{id}")
  public String findById() {

    return "member/memberFrom";
  }
  //수정화면 get /members/{id}/edit
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {

    Member findedMember = memberSVC.findById(id);
    model.addAttribute("member", findedMember);
    return "member/editForm";  //수정화면
  }
  //수정처리 poset /members/{id}/edit EditForm
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, EditForm editForm) {
    Member member = new Member();
    member.setPw(editForm.getPw());
    member.setNickname(editForm.getNickname());

    int updatedRow = memberSVC.update(id, member);
    if (updatedRow == 0) {
      return "member/editForm";
    }else {
      return "redirect:/members/{id}";
    }
  }
  //탈퇴화면 get /members/out outForm
  @GetMapping("/members/del")
  public String delForm() {

    return "member/delForm";
  }
  //탈퇴처리 get /members/{id}/del
  @PostMapping("/{id}/del")
  public String del(@PathVariable("id") Long id, @RequestParam("pw") String pw) {

    int deletedRow = memberSVC.del(id, pw);
    if (deletedRow == 0) {
      return "members/delForm";
    }
    return "redirect:/";  //집으로...
  }
  //목록화면 get /members <- admin


}
