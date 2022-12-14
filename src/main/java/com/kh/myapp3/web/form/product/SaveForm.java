package com.kh.myapp3.web.form.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SaveForm {
  private String pname;     //상품명 PNAME	VARCHAR2(30 BYTE)	Yes		2
  private Integer quantity; //수량 QUANTITY	NUMBER(10,0)	Yes		3
  private Integer price;    //가격 PRICE	NUMBER(10,0)	Yes		4

}
