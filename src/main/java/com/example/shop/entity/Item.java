package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
//20240223-2
@Entity
@Setter
@Getter
@ToString
public class Item {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품코드

    @Column(nullable = false)
    private String itemNm; //상품이름

    @Column(nullable = false)
    private int price; //상품가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품판매가능 상태

    @Column
    private LocalDateTime regTime; //상품 최초 등록 날짜
    @Column
    private LocalDateTime updateTime; //상품 수정 날짜
}


/*
    @Entity - 클래스를 엔티티로 선언 하겠다.(데이터베이스 테이블 매핑)
    @Table - 엔티티와 매핑할 테이블 이름 설정 @Table(name="item_s")

    @Id - 테이블의 기본키(primary key) 지정
    @GeneratedValue - 기본키의 값을 생성하는 방법 명시
                    GenerationType.IDENTITY : 기본키 생성(기본값 생성)
                    GenerationType.AUTO : 자동증가
                    GenerationType.SEQUENCE : 데이터베이스 시퀀스 오브젝트를 이용한 값생성
    @Column - 필드와 테이블의 컬럼 매핑
            @Column(속성= )
            name : 필드와 매핑할 컬럼의 이름 설정
            unique : 유니크 제약조건 설정(중복X)
            insertable: insert 가능 여부
            updateable : update 가능 여부
            nullable : null값 허용 여부 (기본값은 null 이므로 null설정시  )
            columnDefinition : 데이터베이스 칼럼 정보 직접기술
                    @Column(columnDefinition = " name varchar(30) not null ")
            pecition , scale : BigDecimal 타입에서 사용, percision은 소수점 포함 전체 자리수,
                        scale은 소수점 자리수

    @Lob - BLOB , CLOB 타입 매핑, 대용량데이터 저장 가능
            BLOB : 이진데이터를 저장, 이미지나 파일 저장
            CLOB : 문자열나 텍스트 데이터 저장
    @CreationTimestamp - insert시 시간 자동저장
    @UpdateTimestemp - update시 시간 자동저장
    @Enumerated - enum 타입매핑
    @Transient - 해당필드 데이터베이스 매핑 무시
    @Temporal - 날짜탑입매핑
    @CreateDate - 엔티티가 생성되어 저장될때 시간 자동 저장
    @LastModifiedDate - 조회한 엔티티의 값을 변경할때 시간 자동저장





*/
