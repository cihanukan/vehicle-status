package com.softavail.service.client;

import com.softavail.common.exception.VehicleStatusServerErrorException;
import com.softavail.common.exception.VehicleStatusServiceUnavailableException;
import com.softavail.dto.InsuranceReportResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.CircuitBreaker;
import io.micronaut.retry.annotation.Retryable;

@Client(value = "${clients.vehicleStatus}")
@Retryable(delay = "2s", attempts = "3")
public interface InsuranceClient {

    @Get("/accidents/report{?vin}")
    InsuranceReportResponse getInsuranceReport(final String vin) throws VehicleStatusServerErrorException;
}
