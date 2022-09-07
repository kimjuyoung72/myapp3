package com.kh.myapp3.web.admin;

import com.kh.myapp3.domain.Member;
import com.kh.myapp3.domain.admin.AdminMemberSVC;
import com.kh.myapp3.web.admin.form.member.AddForm;
import com.kh.myapp3.web.admin.form.member.EditForm;
import com.kh.myapp3.web.admin.form.member.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class AdminMemberController {

  private final AdminMemberSVC adminMemberSVC;
  //등록화면
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("form", new AddForm());
    return "admin/member/addForm";
  }

  //가입처리 post /members/add  AddFrom
  @PostMapping("/add")
  public String add(@ModelAttribute AddForm addForm) {
    //검증
    log.info("addForm:{}", addForm);  //toString()이 처리.

    if (addForm.getEmail().trim().length() == 0) {

      return "admin/member/addForm";
    }
    //회원등록
    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPw(addForm.getPw());
    member.setNickname(addForm.getNickname());
    Member insertedMember = adminMemberSVC.insert(member);

    return "redirect:/admin/members/"+insertedMember.getMemberId(); //회원 상세 화면
  }
  //조회화면 get /members/{id} MemberForm
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {

    Member findedMember = adminMemberSVC.findById(id);

    MemberForm memberForm = new MemberForm();
    memberForm.setMemberId(findedMember.getMemberId());
    memberForm.setEmail(findedMember.getEmail());
    memberForm.setPw(findedMember.getPw());
    memberForm.setNickname(findedMember.getNickname());
    memberForm.setCdate(findedMember.getCdate());
    memberForm.setUdate(findedMember.getUdate());

    model.addAttribute("memberForm", memberForm);
    return "admin/member/memberForm";
  }
  //수정화면 get /members/{id}/edit
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {

    Member findedMember = adminMemberSVC.findById(id);

    EditForm editForm = new EditForm();
    editForm.setMemberId(findedMember.getMemberId());
    editForm.setEmail(findedMember.getEmail());
    editForm.setPw((findedMember.getPw()));
    editForm.setNickname(findedMember.getNickname());

    model.addAttribute("editForm", editForm);
    return "admin/member/editForm";  //수정화면
  }
  //수정처리 poset /members/{id}/edit EditForm
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, EditForm editForm) {
    Member member = new Member();
    member.setPw(editForm.getPw());
    member.setNickname(editForm.getNickname());

    int updatedRow = adminMemberSVC.update(id, member);
    if (updatedRow == 0) {
      return "admin/member/editForm";
    }
    return "redirect:/admin/members/{id}";

  }

  //탈퇴처리 get /members/{id}/del
  @GetMapping("/{id}/del")
  public String del(@PathVariable("id") Long id) {

    int deletedRow = adminMemberSVC.del(id);
    if (deletedRow == 0) {
      return "redirect:/admin/members/"+id;
    }
    return "redirect:/admin/members/all";
  }
  //목록화면 get /members <- admin
  @GetMapping("/all")
  public String all(Model model) {

    List<Member> list = adminMemberSVC.all();
    model.addAttribute("list", list);
    return "admin/member/all";
  }
}
