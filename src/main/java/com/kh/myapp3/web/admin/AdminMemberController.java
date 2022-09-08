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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    //참조 당시에 'addForm'이 존재하지 않기에 View의 object가 참조할 수 있도록...
    model.addAttribute("form", new AddForm());

    return "admin/member/addForm";
  }

  //가입처리 post /members/add  AddFrom
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute("form") AddForm addForm,
                    BindingResult bindingResult,
                    RedirectAttributes redirectAttributes) {

    log.info("addForm:{}", addForm);
    if (bindingResult.hasErrors()) {
      log.info("errors:{}", bindingResult);
      return "admin/member/addForm";
    }
    //ID 중복체크
    Boolean isExist = adminMemberSVC.dupChkOfMemberEmail(addForm.getEmail());
    if (isExist) {
      bindingResult.rejectValue("email", "dup.email", "동일한 이메일이 존재합니다.");
      return "admin/member/addForm";
    }
    //회원등록
    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPw(addForm.getPw());
    member.setNickname(addForm.getNickname());
    Member insertedMember = adminMemberSVC.insert(member);

    Long id = insertedMember.getMemberId();
//    log.info("id:{}", id);
    redirectAttributes.addAttribute("id", id);  //리다이렉트할 때
          //이전 정보를 가지고 있지 않기 때문에 id를 사용하기 위해..
    return "redirect:/admin/members/{id}"; //회원 상세 화면
  }

  public String add2(@Valid @ModelAttribute("form") AddForm addForm, BindingResult bindingResult) {
    //검증기 @Valid로 바인딩되면서 검증 후 @ModelAtt...적용
    // @Valid -> @NotBlack 로 AddForm 멤버 체크. 모든 View단에서 'form'이라는 이름으로 접근 가능하게 모델 정의.
    // @ModelAttribute -> model.addAttribute("addForm", addForm);
    // 다음에 오는 폼이름 'AddForm'을  소문자로 시작하는 이름 'addForm'으로
    // 바꿔서 적용 화면에서 form 객체로 접근가능하게 한다.
    // Error -> bingResult -> model, 항상 폼객체 뒤에 와야됨. 따라서 바로 앞에 있는 객체의
    // 정보(여기서는 addForm)를 가지고 있다.
    // View에서 #fieds로 접근 가능.

    log.info("addForm:{}", addForm);  //toString()이 처리.

//    if (addForm.getEmail().trim().length() == 0) {
//
//      return "admin/member/addForm_old";
//    }
    //바인딩 객체에 있는 오류로 체크
    if (bindingResult.hasErrors()) {
      log.info("errors:{}", bindingResult);
      return "admin/member/addForm_old";
    }

    //비즈니스 규칙
    //1)이메일에 '@'가 없으면 오류. field 오류는 rejectValue();를 이용.
    if (!addForm.getEmail().contains("@")) {

//      bindingResult.rejectValue("email", "emailChk", new String[]{"",""}, "이메일 형식에 맞지 않습니다.");
      bindingResult.rejectValue("email", "emailChk", "이메일 형식에 맞지 않습니다.");
      return "admin/member/addForm_old";
    }
    if (addForm.getEmail().length() > 5) {
      bindingResult.rejectValue("email", "emailChk2", new String[]{"0","5"}, "이메일 길이가 초과 했습니다.");
      return "admin/member/addForm_old";
    }
    //2)objectError 2개 이상의 필드분석을 통해 오류검증. -> Global 오류는 reject();를 이용.
    //비밀번호, 별칭의 자릿수가 모두 5미만인 경우 전체 조합하여 분석하고 별도의 곳에 메시지 표시.
    if (addForm.getPw().trim().length() < 8 && addForm.getNickname().trim().length() < 5) {

      bindingResult.reject("memberChk", new String[]{"5"},"비밀번호, 별칭의 자리수 모두 5이상이어야 합니다.");
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

    model.addAttribute("form", memberForm);
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

    model.addAttribute("form", editForm);
    return "admin/member/editForm";  //수정화면
  }
  //수정처리 poset /members/{id}/edit EditForm
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id,  @Valid @ModelAttribute("form"), EditForm editForm) {
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
