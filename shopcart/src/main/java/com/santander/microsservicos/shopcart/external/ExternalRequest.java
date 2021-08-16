/*
 * Biblioteca para obter dados JSON por requisições HTTP com modelagem de classes em Java.
 * Autor: Mateus Araújo.
 * https://github.com/arj-mat
 */

package com.santander.microsservicos.shopcart.external;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class ExternalRequest<O> {
    private Supplier<O> resultModelSupplier;

    public ExternalRequest(Supplier<O> resultModelSupplier) {
        this.resultModelSupplier = resultModelSupplier;
    }

    protected ExternalResult<O> execute(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        Integer responseCode = -1;

        try {
            HttpResponse<String> response = client.send( request, HttpResponse.BodyHandlers.ofString() );

            responseCode = response.statusCode();

            ExternalResult<O> externalResult = new ExternalResult<>( responseCode );

            try {
                ObjectMapper mapper = new ObjectMapper();
                externalResult.data = Optional.ofNullable( (O) mapper.readValue( response.body(),
                                                                                 resultModelSupplier.get().getClass() ) );
            } catch ( Exception e ) {
                System.err.printf(
                        "[%s] [%s] [%s] [%d]\nFailed to parse JSON:\n",
                        this.getClass().getName(),
                        request.method(),
                        request.uri(),
                        responseCode
                );

                System.err.println( e );
            }

            return externalResult;
        } catch ( Exception e ) {
            System.err.printf(
                    "[%s] [%s] [%s] [%s]\nFailed to execute.\n",
                    this.getClass().getName(),
                    request.method(),
                    request.uri(),
                    ( responseCode != -1 ?
                      responseCode.toString() :
                      "no response" )
            );

            e.printStackTrace();

            return new ExternalResult<O>( responseCode );
        }
    }
}
