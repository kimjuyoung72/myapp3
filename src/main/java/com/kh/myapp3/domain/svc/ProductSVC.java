package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Product;

public interface ProductSVC {

   /**
    * 상품등록
    * @param product 상품정보
    * @return  상품아이디
    */
   Integer save(Product product);


}
