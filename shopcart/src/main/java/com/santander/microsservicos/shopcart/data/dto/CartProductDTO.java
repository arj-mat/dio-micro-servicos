package com.santander.microsservicos.shopcart.data.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {
    private long productID;
    private String name;
    private String thumbnail;
    private float price;
    private int amount;
}
