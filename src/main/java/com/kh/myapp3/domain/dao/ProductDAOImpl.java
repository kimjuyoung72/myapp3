package com.kh.myapp3.domain.dao;

import com.kh.myapp3.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository   //DAO
@RequiredArgsConstructor //final필드를 생성자의 매개변수로 해서 생성자를 만들어줌.
public class ProductDAOImpl implements ProductDAO{

  private final JdbcTemplate jt;

  //  생성자 주입
  // final인 필드를 생성자의 매개변수로 해서 생성자를 만들어줌.
  //-------------------------------------------@RequiredArgrsConstruct
//  ProductDAOImpl(JdbcTemplate jt){
//    this.jt = jt;
//  }
  //-------------------------------------------@RequiredArgrsConstruct
  //등록
//  @Override
//  public Integer save(Product product) {
//    StringBuffer sql = new StringBuffer();  //문자열 연결. "..." + "..." = "......"한줄일 경우 String으로 만들어도 된다.
//    sql.append("insert into product values(product_product_id_seq.nextval,?,?,?)");//?는 이후
//                                                                                   //PreparedStatement 객체에 의해 채워진다.
//
//    class PreparedStatementCreatorImpl implements PreparedStatementCreator{
//
//      @Override
//      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//        PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"product_id"});
//        pstmt.setString(1,product.getPname());
//        pstmt.setInt(2,product.getQuantity());
//        pstmt.setInt(3,product.getPrice());
//        return pstmt;
//      }
//    }
//
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//    jt.update(new PreparedStatementCreatorImpl(), keyHolder); //익명 클래스로 인터페이스 구현. new interface이름(){}
//    Integer product_id = Integer.valueOf(keyHolder.getKeys().get("product_id").toString());
//    return product_id;
//  }
  @Override
  public Product save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product values(product_product_id_seq.nextval,?,?,?)");

    KeyHolder keyHolder = new GeneratedKeyHolder(); //저장한 데이터를 바로 꺼내기위한 변수
    jt.update(new PreparedStatementCreator(){ //?에 치환할 값을 할당. keyholder를 사용하기 위해
                                              //PreparedStatementCreate psc, KeyHolder keyholder가
                                              //매개 변수인 update()를 사용.
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"product_id"});
        pstmt.setString(1,product.getPname());  //?의 index를 맞춰야됨.
        pstmt.setInt(2,product.getQuantity());
        pstmt.setInt(3,product.getPrice());
        return pstmt;
      }
    }, keyHolder);

    long product_id = Long.parseLong(keyHolder.getKeys().get("product_id").toString());
    product.setProductId((product_id));
    return product;
  }

  //조회

  @Override
  public Product findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price ");
    sql.append("from product ");
    sql.append("where product_id = ? ");
    Product product = null; //개별조회 queryForObject()
    try {
      //단일 레코드 => queryForObject(). BeanPropertyRowMapper<>()는 대응하는 것에 자동매핑 한다. productId는 찾는 대상.
      product = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class), productId);
    } catch (EmptyResultDataAccessException e) {  //대응되는게 없다면 예외 발생
      log.info("삭제대상 상품이 없습니다. 상품아이디={}", productId);
    }
    return product;
  }
  //수정
  @Override
  public void update(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();

    sql.append("update product ");
    sql.append("   set pname = ?, ");
    sql.append("       quantity = ?, ");
    sql.append("       price = ? ");
    sql.append(" where product_id = ? ");


    jt.update(sql.toString(), product.getPname(), product.getQuantity(), product.getPrice(), productId);


  }


  @Override
  public void delete(Long productId) {
    String sql = "delete from product where product_id = ? ";
    jt.update(sql, productId);
  }

  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select product_id, pname, quantity, price ");
    sql.append("  from product ");

    //case1) sql 결과 레코드와 동일한 구조의 java객체가 존재할 경우
    //여러 컬럼이 있을 경우 query() 사용. 이 경우 리턴은 컬렉션이 되야 한다.
    List<Product> result =
        jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class)); //여러 건 조회. rowMap에 자동 매핑

    //case2) sql 결과 레코드의 컬럼명과 java 객체의 멤버이름이 다른 경우 or 타입이 다른 경우 수동매핑
//      jt.query(sql.toString(), new RowMapper<Product>() {
//
//        @Override
//        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
//          Product product = new Product();
//          product.setProductId(rs.getLong("product_id"));
//          product.setQuantity(rs.getInt("quantity"));
//          product.setPrice(rs.getInt("price"));
//
//          return product;
//        }
//      });


    return result;
  }
//  @Override
//  public Integer save(Product product) { //메소드가 하나일경우 람다식으로 가능.
//    StringBuffer sql = new StringBuffer();
//    sql.append("insert into product values(product_product_id_seq.nextval,?,?,?)");
//
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//    jt.update(
//      con->{
//        PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"product_id"});
//        pstmt.setString(1,product.getPname());
//        pstmt.setInt(2,product.getQuantity());
//        pstmt.setInt(3,product.getPrice());
//        return pstmt;
//      }
//    , keyHolder);
//
//    Integer product_id = Integer.valueOf(keyHolder.getKeys().get("product_id").toString());
//    return product_id;
//  }

  @Override
  public void deleteAll() {
    String sql = "delete from product";
    int deletedRows = jt.update(sql); //삭제건수 반환
    log.info("삭제건수: {}", deletedRows);
  }

  @Override
  public Long generatePid() {
    String sql = "select product_product_id_seq.nextval from dual"; //현재
//    String sql = "select product_product_id_seq.currtval from dual";  //가장최근
    Long seq = jt.queryForObject(sql, Long.class);  //단일 레코드, 컬럼
    return seq;
  }
}
