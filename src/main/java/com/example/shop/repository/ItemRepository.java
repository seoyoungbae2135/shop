package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//20240223
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    // 상품이름으로 조회
    List<Item> findByItemNm(String itemNm);

    // 상품명, 상품상세설명으로 검색
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //상품이 30000원이상인 상품조회
    //List<Item> findByPrice(int price);

    //상품가격이하 조회
    List<Item> findByPriceLessThanEqual(Integer price);  // int보다 class인 Integer로 사용한다


    //상품의 가격이 15000원에서 35000원사이의 상품들만조회해서 상품가격 내림차순으로 가져오기. 20240223-5,6
    List<Item> findByPriceBetweenOrderByPriceDesc(Integer price1, Integer price2);

    //상품상세설명 JPQL 테스트
    // findByItemDetailLikeOrderByPriceDesc(String itemDetail) 이렇게 작성해야하는 것을 아래와같이 여기라인+2번째 라인 과같이 작성
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") //쿼리 에너테이션.
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); //이방식을 JPQL 이라고한다
    //위의것을 원래의 쿼리문으로 쓰면 아래와 같다. 두개가 동일하게 동작하는 것이다.
    @Query(value="select * from item where item_detail like %:itemDetail% order by price desc",
        nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

}
/*
    JPA와 JQPL의 단점은 에러발견이 안된다
    문자열로 쿼리를 입력하기 때문에 잘못입력하여도 컴파일 시점에서는 에러를 발견할 수 없다.
    그래서 단점을 보완하고자 만들어진 녀석이 Querydsl 이다.

    querydsl 의 장점
    - 고정된 쿼리문이 아닌 조건에 맞게 동적으로 쿼리를 생성할 수 있다
    - 비슷한 쿼리를 재사용할 수 있으며 제약 조건 조립 및 가독성을 향상시킬 수 있다.
    - 문자열이 아닌 자바 소스코드로 작성하기 때문에 컴파일 시점에 오류를 발견 할 수 있다.
    - IDE 의 도움으로 자동완성기능 사용해서 생산성 향상 시킬 수 있다.
 */

/*
    @Query 에너테이션을 이용하여 sql과 유사한 JPQL이라는 객체지향 쿼리 언어를 통해
    복잡한 쿼리도 처리가 가능하다.
    파라미터(:itemDetail)에 @Param 애너테이션을 이용하여 파라미터로 넘어온값을 JPQL에
    들어갈 변수로 지정할 수 있다
        :는 변수앞에 붙여서 사용한다.
    @Query("select * from Item where price < :price")
    List<Item> priceThan(@Param("price") Integer price);
 */

// jpa의 네이밍규칙을 이용하여 메서드를 작성하면 원하는 쿼리를 실행할 수 있다.
// 데이터베이스에서 조회할 메서드 규칙 find + By + 변수이름
//      findByItemNm - 상품이름으로 조회하는 메서드, 변수이름 앞글자는 대문자로 한다

//

/*
    keyword             sample                      query
    
    And                 findByItemAndPrice          where item_nm='a' and price=1000
    Or                  findByItemOrPrice           where item_nm='a' or price=1000
    Is, Equals          findByItemNm
                        findByItemNmIs              where item_nm='a' (3가지 sample에 쿼리문은 한개로 적용)
                        findByItemNmEquals
    Between             findByRegDateBetween        where reg_date between 2024-01-01 and 2024-02-03, 범위검색
    LessThan            findByPriceLessThen         where price < 50000,  50000보다 작은것 검색
    LessThanEqual       findByPriceLessThanEqual    where price <= 50000, 이하검색
    GreaterThan         findByPriceGreaterThan      where price > 20000, 큰것 검색
    GreaterThanEqual    findByPriceGreaterEqual     where price >=20000, 이상검색
    After               findByRegDateAfter          where reg_date > 2024-01-01
    Before              findByRegDateBefore         where reg_date < 2024-02-15
    IsNull,Null         findByItemNmNull            where item_nm is null
    NotNull             findByItemNmNotNull         where item_nm not null
    Like                findByItemNmLike            where item_nm like '가방'
    StartingWith        findByItemNmStartingWith    where item_nm like '가방%' 가방으로 시작되는것 검색
    EndingWith          findByItemNmEndingWith      where item_nm like '%가방' 가방으로 끝나는 것 검색
                        findByItemNmStartingWithIgnoreCase
    OrderBy             findByPriceOrderByRegDateDesc     where price=3000 order by reg_date desc 내림차순
    
    


 */