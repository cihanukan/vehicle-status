package com.softavail.service.client;

import com.softavail.dto.InsuranceReportResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;

@Client(value = "${clients.vehicleStatus}")
@Retryable(delay = "s", attempts = "3")
public interface InsuranceClient {

    @Get("/accidents/report{?vin}")
    InsuranceReportResponse getInsuranceReport(final String vin);
}
