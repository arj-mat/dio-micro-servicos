package com.santander.microsservicos.shopcatalog.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopItemCategory {
    RARE( 1 ),
    EPIC( 2 ),
    LEGENDARY( 3 );

    private final Integer category;
}
