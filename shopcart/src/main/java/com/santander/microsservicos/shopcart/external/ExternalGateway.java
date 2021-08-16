package com.santander.microsservicos.shopcart.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExternalGateway {
    CART( "cart" ),
    CATALOG( "product" );

    private final String gateway;
}
