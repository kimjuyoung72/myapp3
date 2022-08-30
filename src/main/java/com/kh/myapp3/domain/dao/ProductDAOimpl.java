package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Slf4j
@Repository
@RequiredArgsConstructor  // final인 필드를 생성자의 매개변수로 해서 생성자를 만들어줌.
public class ProductDAOimpl implements ProductDAO{

  //  컨테이너에 객체화 되어 이미 올라와 있기에 jdbc객체를 생성할 필요가 없다
  private final JdbcTemplate jt;

  //-------------------------------------------@RequiredArgrsConstruct
  //생성자 주입
//
//  ProductDAOimpl(JdbcTemplate jt) {   //생성자 주입
//    this.jt = jt;
//  }
  //-------------------------------------------@RequiredArgrsConstruct

  @Override
  public Integer save(Product product) {

    StringBuffer sql = new StringBuffer();  //문자열 연결. "..." + "..." = "......"

//    한줄일 경우 String으로 만들어도 된다.
    sql.append("insert into product values(product_product_id_seq.nextval,?,?,?)"); //?는 이후
      //PreparedStatement 객체에 의해 채워진다.
//    sql.append("insert into product (product_id, pname, quantity, price) ");
//    sql.append("values(product_product_id_seq.nextval,'모니터',10,900000) ");
//    class PreparedStatementCreatorImpl implements PreparedStatementCreator{
//
//      @Override
//      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"product_id"}); //insert 이후 반환할 컬럼
//        pstmt.setString(1, product.getPname());
//        pstmt.setInt(2, product.getQuantity());
//        pstmt.setInt(3, product.getProductId());
//        return pstmt;
//      }
//    }
    KeyHolder keyHolder = new GeneratedKeyHolder();
//    jt.update(new PreparedStatementCreatorImpl(), keyHolder);
    jt.update(new PreparedStatementCreator(){   //익명 클래스로 인터페이스 구현. new interface이름(){}

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
          PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"product_id"}); //insert 이후 반환할 컬럼
          pstmt.setString(1, product.getPname());
          pstmt.setInt(2, product.getQuantity());
          pstmt.setInt(3, product.getProductId());
          return pstmt;

      }
    }, keyHolder);
    Integer product_id = Integer.valueOf(keyHolder.getKeys().get("product_id").toString());

    return product_id;

//  }
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//
//    jt.update( con -> {     //메소드가 하나일경우 람다식으로 가능.
//        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"product_id"}); //insert 이후 반환할 컬럼
//        pstmt.setString(1, product.getPname());
//        pstmt.setInt(2, product.getQuantity());
//        pstmt.setInt(3, product.getProductId());
//        return pstmt;
//    }, keyHolder);
//    Integer product_id = Integer.valueOf(keyHolder.getKeys().get("product_id").toString());
//
//    return product_id;

  }
}
