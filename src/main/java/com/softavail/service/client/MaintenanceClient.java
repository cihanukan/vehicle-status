package com.softavail.service.client;

import com.softavail.dto.MaintenanceResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client(value = "${clients.vehicleStatus}")
public interface MaintenanceClient {

    @Get("/cars/{vin}")
    MaintenanceResponse getMaintenanceInfo(@PathVariable String vin);
}
