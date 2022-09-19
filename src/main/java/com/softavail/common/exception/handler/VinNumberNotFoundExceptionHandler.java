package com.softavail.common.exception.handler;

import com.softavail.common.enums.VehicleStatusErrorCode;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.dto.ExceptionDTO;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Produces
@Primary
@Requires(classes = {VinNumberNotFoundException.class, ExceptionHandler.class})
public class VinNumberNotFoundExceptionHandler implements ExceptionHandler<VinNumberNotFoundException, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(VinNumberNotFoundExceptionHandler.class);
    @Override
    public HttpResponse handle(HttpRequest request, VinNumberNotFoundException exception) {
        logger.debug("Caught exception", exception);
        final ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setErrorName(VehicleStatusErrorCode.VIN_NOT_FOUND.name());
        exceptionDTO.setErrorCode(VehicleStatusErrorCode.VIN_NOT_FOUND.toString());
        exceptionDTO.setErrorDescription(exception.getMessage());

        return HttpResponse.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }
}
