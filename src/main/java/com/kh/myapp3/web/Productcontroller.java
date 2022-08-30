package com.kh.myapp3.web;

import com.kh.myapp3.domain.Product;
import com.kh.myapp3.domain.svc.ProductSVC;
import com.kh.myapp3.web.form.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class Productcontroller {

  private final ProductSVC productSVC;
//  등록양식
  @GetMapping
  public String saveForm() {

    return "product/saveForm";
  }
//GET, POST, PUT, PATCH, DELETE
//  등록처리
  @PostMapping  //요청 메시지 바디에 매칭되는 키가 있다면 Setter 메소드를 통해 데이터를 반영한다.
                // -> form태그의 입력란에 name 속성과 매칭이 필요하다.
  public String saver(SaveForm saveForm) {  //saveForm에 먼저 데이터를 넣고 saver를 호출한다.
    log.info("saveForm:{}", saveForm);    //{} 에 뒤의 변수대입하여 콘솔에 보여준다. Slf4j
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice((saveForm.getPrice()));


    Integer productId = productSVC.save(product);

    return "redirect:/product/"+productId; //전체목록 View
  }

//  상품조회
  @GetMapping("/{pid}")
  public String findByProductId(@PathVariable("pid") String pid) {
//    DB에서 상품조회
    return "product/detailForm";  //상품 상세 View
  }

  //  상품수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm() {

    return "product/updateForm";  //상품 수정 View
  }
//  상품수정
  @PostMapping("/{pid}/edit")
  public String update() {

    return "redirect:/product/1"; //상품 상세 View
  }
//  삭제처리
  @GetMapping("{pid}/del")
  public String delete() {

    return "redirect:/product/all"; //전체목록 View
  }

  //  목록화면
  @GetMapping("/all")
  public String list() {

    return "product/all";   //전체목록 View
  }

}
