package com.santander.microsservicos.shopcart.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false, columnDefinition = "integer default 1")
    private int amount;
}
