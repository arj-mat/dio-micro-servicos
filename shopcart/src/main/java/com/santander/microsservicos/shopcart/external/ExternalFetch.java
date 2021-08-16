package com.santander.microsservicos.shopcart.external;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class ExternalFetch<O> {
    private Supplier<O> dataSupplier;

    public ExternalFetch(Supplier<O> dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    public ExternalDataResult<O> get(ExternalGateway targetGateway, String path) {
        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri( URI.create( String.format( "http://localhost:8080/%s/%s", targetGateway.getGateway(), path ) ) )
                .build();

        try {
            HttpResponse<String> response = client.send( request, HttpResponse.BodyHandlers.ofString() );
            ExternalDataResult<O> externalDataResult = new ExternalDataResult<>( response.statusCode() );

            try {
                ObjectMapper mapper = new ObjectMapper();
                externalDataResult.data = Optional.ofNullable( (O) mapper.readValue( response.body(),
                                                                                     this.dataSupplier.get().getClass() ) );
            } catch ( Exception e ) {
                externalDataResult.data = Optional.empty();

                System.err.printf( "%s [%d]%n", path, response.statusCode() );
                System.err.println( e );
                System.out.println( response.body() );
            }

            return externalDataResult;
        } catch ( Exception e ) {
            return new ExternalDataResult<>( -1 );
        }
    }
}
