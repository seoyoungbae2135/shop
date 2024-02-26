package com.example.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ItemImg extends BaseEntity {
    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String imgName;
    @Column
    private String oriImgName;
    @Column
    private String imgUrl;
    @Column
    private String repImgYn;

    /*@Column 20240226-7 수정
    private LocalDateTime regDate;
    @Column
    private LocalDateTime updateDate;*/

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;



}
