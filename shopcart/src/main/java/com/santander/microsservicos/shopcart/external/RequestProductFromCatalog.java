package com.santander.microsservicos.shopcart.external;

import com.santander.microsservicos.shopcart.external.model.ProductFromCatalog;

import java.net.URI;
import java.net.http.HttpRequest;

public class RequestProductFromCatalog extends ExternalRequest<ProductFromCatalog> {
    public RequestProductFromCatalog() {
        super( ProductFromCatalog::new );
    }

    public ExternalResult<ProductFromCatalog> byID(Long id) {
        return this.execute(
                HttpRequest
                        .newBuilder()
                        .GET()
                        .uri( URI.create( String.format( "http://localhost:8080/product/%d", id ) ) )
                        .build()
        );
    }
}
