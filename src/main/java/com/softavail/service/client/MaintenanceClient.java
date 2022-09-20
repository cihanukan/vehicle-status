package com.softavail.service.client;

import com.softavail.dto.MaintenanceResponse;
import io.micronaut.http.annotation.*;
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
@Retryable(delay = "2s", attempts = "3")
public interface MaintenanceClient {

    @Get("/cars/{vin}")
    MaintenanceResponse getMaintenanceInfo(@PathVariable String vin);
}
