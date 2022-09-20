package com.softavail.service.client;

import com.softavail.dto.InsuranceReportResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;

/***
 * Insurance and maintenance client call supplied under same url.
 * Wiremock is up at http://localhost:8889
 * The calls seperated with endpoints
 * Insurance client call => http://localhost:8888/accident/report?vin={vinNumber}
 * Maintenance client call => http://localhost:8888/cars/{vinNumber}
 *  ***/
@Client(value = "${clients.vehicleStatus}")
@Retryable(delay = "s", attempts = "3")
public interface InsuranceClient {

    @Get("/accidents/report{?vin}")
    InsuranceReportResponse getInsuranceReport(final String vin);
}
