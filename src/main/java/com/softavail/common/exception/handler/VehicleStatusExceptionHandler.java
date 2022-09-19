package com.softavail.common.exception.handler;

import com.softavail.common.enums.VehicleStatusErrorCode;
import com.softavail.common.exception.VehicleStatusException;
import com.softavail.dto.ExceptionDTO;
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
@Requires(classes = {VehicleStatusException.class, ExceptionHandler.class})
public class VehicleStatusExceptionHandler implements ExceptionHandler<VehicleStatusException, HttpResponse>{

    private static final Logger logger = LoggerFactory.getLogger(VehicleStatusExceptionHandler.class);

    @Override
    public HttpResponse<ExceptionDTO> handle(HttpRequest request, VehicleStatusException exception) {
        logger.debug("Error occured. Exception : ", exception);

        HttpStatus httpStatus = getHttpStatus(exception.getErrorCode());
        ExceptionDTO exceptionDTO =  new ExceptionDTO();
        exceptionDTO.setErrorCode(exception.getErrorCode().toString());
        exceptionDTO.setErrorDescription(exception.getErrorDescription());

        return HttpResponse.status(httpStatus).body(exceptionDTO);
    }

    private HttpStatus getHttpStatus(VehicleStatusErrorCode errorCode){
         switch (errorCode) {
             case VIN_NOT_FOUND: return HttpStatus.NOT_FOUND;
             case SERVER_ERROR: return  HttpStatus.INTERNAL_SERVER_ERROR;
             case SERVICE_UNAVAILABLE: return HttpStatus.SERVICE_UNAVAILABLE;
        };
        return null;
    }
}
