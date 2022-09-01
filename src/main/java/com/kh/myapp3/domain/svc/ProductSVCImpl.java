package com.kh.myapp3.domain.svc;

import com.kh.myapp3.domain.Product;
import com.kh.myapp3.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service    //SVC
@RequiredArgsConstructor    //생성자 주입
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

//  ProductSVCImpl(ProductDAO productDAO) {
//    this.productDAO = productDAO;
//  }

//  등록
  @Override
  public Product save(Product product) {
    return productDAO.save(product);
  }

  @Override
  public void update(Long productId, Product product) {
    productDAO.update(productId, product);
  }

  @Override
  public Product findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public void delete(Long productId) {
    productDAO.delete(productId);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public void deleteAll() {
    productDAO.deleteAll();
  }
}
