package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Product;

import java.util.List;

public interface ProductSVC {

   /**
    * 상품등록
    * @param product 상품정보
    * @return  상품아이디
    */
   Product save(Product product);

   /**
    * 수정
    * @param product 수정할 상품정보
    */
   void update(Long productId, Product product);

   /**
    * 조회
    * @param productId 상품아이디
    * @return 상품정보
    */
   Product findById(Long productId);

   /**
    * 삭제
    * @param productId 상품아이디
    */
   void delete(Long productId);

   /**
    * 목록
    * @return 상품 전체 목록
    */
   List<Product> findAll();

   /**
    * 전체삭제
    */
   void deleteAll();
}
