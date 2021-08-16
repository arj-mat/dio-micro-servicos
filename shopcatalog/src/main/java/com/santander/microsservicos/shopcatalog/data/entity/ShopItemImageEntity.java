package com.santander.microsservicos.shopcatalog.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shop_item_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopItemImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String url;
}