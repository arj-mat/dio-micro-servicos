package com.santander.microsservicos.shopcart.external;

import lombok.Getter;

import java.util.Optional;

@Getter
public class ExternalDataResult<T> {
    public int status;
    public Optional<T> data;

    ExternalDataResult(int status) {
        this.status = status;
    }
}
