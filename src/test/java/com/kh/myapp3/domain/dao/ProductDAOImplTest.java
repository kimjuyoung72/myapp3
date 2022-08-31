package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired  //구현된 같은 이름의 객체가 있다면 주입
  ProductDAO productDAO;

  @Test
  @DisplayName("상품목록")
  void save() {

    Product product = new Product();
    product.setPname("외장하드");
    product.setQuantity(1);
    product.setPrice(50000);

    Product savedProduct = productDAO.save(product);
    log.info("savedProduct={}", savedProduct);
  }

  @Test
  @DisplayName("조회")
  void findById() {
    Long productId = 63L;

    Product findedProduct = productDAO.findById(productId);
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("외장하드");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(1);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(50000);
  }

  @Test
  @DisplayName("수정")
  void update() {

    Long productId = 63L;
    Product product = new Product();
    product.setProductId(productId);
    product.setPname("선풍기");
    product.setQuantity(15);
    product.setPrice(50000);
    productDAO.update(productId, product);

    Product findedProduct = productDAO.findById(productId);
//    Assertions.assertThat(findedProduct.getPname()).isEqualTo(findedProduct.getPname());
//    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(findedProduct.getQuantity());
//    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(findedProduct.getPrice());
    Assertions.assertThat(findedProduct).isEqualTo(product);
  }

  @Test
  @DisplayName("삭제")
  void delete() {

    Long productId = 63L;
    productDAO.delete(productId);
    Product findedProduct = productDAO.findById(productId);
    Assertions.assertThat(findedProduct).isNull();

  }

  @Test
  @DisplayName("목록")
  void all() {
    List<Product> list = productDAO.findAll();
    log.info("전체목록={}", list);
//    list.stream().forEach(ele -> log.info("상품:{}", ele));
    for(Product p : list) {
      log.info("상품:{}", p);
    }

    Assertions.assertThat(list.size()).isEqualTo(5);
  }
}
