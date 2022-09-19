package com.softavail.common.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = {VinNumberNotFoundException.class, ExceptionHandler.class})
public class VinNumberNotFoundExceptionHandler implements ExceptionHandler<VinNumberNotFoundException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, VinNumberNotFoundException exception) {
        return HttpResponse.notFound("Vin number not found");
    }
}
