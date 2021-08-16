package com.santander.microsservicos.shopcart.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CheckoutDTO {
    private List<Long> products;
    private float totalPrice;
}
