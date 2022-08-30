package com.kh.myapp3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/product")
public class Productcontroller {

//  등록양식
  @GetMapping
  public String saveForm() {

    return "product/saveForm";
  }
//GET, POST, PUT, PATCH, DELETE
//  등록처리
  @GetMapping
  public String saver() {

    return "product/all"; //전체목록
  }





}
