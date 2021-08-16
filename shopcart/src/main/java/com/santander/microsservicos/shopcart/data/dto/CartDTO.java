package com.santander.microsservicos.shopcart.data.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private String id;
    private List<CartProductDTO> products;

    @Accessors(chain = true)
    private String alertMessage;
}
