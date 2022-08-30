package com.kh.myapp3.domain;

import lombok.Data;

@Data
public class Product {
  private Integer productId;  //  PRODUCT_ID	NUMBER(10,0)	No		1
  private String pname;       //  PNAME	VARCHAR2(30 BYTE)	Yes		2
  private Integer quantity;   //  QUANTITY	NUMBER(10,0)	Yes		3
  private Integer price;      //  PRICE	NUMBER(10,0)	Yes		4

}
