package com.santander.microsservicos.shopcart.external;

import com.santander.microsservicos.shopcart.external.model.ProductFromCatalogResponse;

public class FetchProductFromCatalog extends ExternalFetch<ProductFromCatalogResponse> {
    public FetchProductFromCatalog() {
        super( ProductFromCatalogResponse::new );
    }
}
