package com.kh.myapp3.web;

import com.kh.myapp3.domain.Product;
import com.kh.myapp3.domain.svc.ProductSVC;
import com.kh.myapp3.web.form.ItemForm;
import com.kh.myapp3.web.form.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  @GetMapping("/add")
  public String saveForm() {

    return "product/addForm";
  }
//GET, POST, PUT, PATCH, DELETE
//  등록처리
  @PostMapping("/add")  //요청 메시지 바디에 매칭되는 키가 있다면 Setter 메소드를 통해 데이터를 반영한다.
                // -> form태그의 입력란에 name 속성과 매칭이 필요하다.
  public String saver(SaveForm saveForm) {  //saveForm에 먼저 데이터를 넣고 saver를 호출한다.
    log.info("addForm:{}", saveForm);    //{} 에 뒤의 변수대입하여 콘솔에 보여준다. Slf4j
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice((saveForm.getPrice()));


    Product savedProduct = productSVC.save(product);

    return "redirect:/products/"+savedProduct.getProductId(); //전체목록 View 요청 url
  }

//  상품개별조회
  @GetMapping("/{pid}")
  public String findByProductId(
          @PathVariable("pid") Long pid,
          Model model
  ) {
    //db에서 상품조회
    Product findedProduct = productSVC.findById(pid);

    //Product => ItemForm 복사
    ItemForm itemForm = new ItemForm();
    itemForm.setProductId(findedProduct.getProductId());
    itemForm.setPname(findedProduct.getPname());
    itemForm.setQuantity(findedProduct.getQuantity());
    itemForm.setPrice(findedProduct.getPrice());

    //view에서 참조하기 위해 model 객체에 저장
    model.addAttribute("itemForm", itemForm);
//    DB에서 상품조회
    return "product/itemForm";  //상품 상세 View
  }

  //  상품수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm() {

    return "product/editForm";  //상품 수정 View
  }
//  상품수정
  @PostMapping("/{pid}/edit")
  public String update() {

    return "redirect:/products/1"; //상품 상세 View 요청 url
  }
//  삭제처리
  @GetMapping("/{pid}/del")
  public String delete() {

    return "redirect:/products"; //전체목록 View 요청 url
  }

  //  목록화면
  @GetMapping
  public String list() {

    return "product/all";   //전체목록 View
  }

}
