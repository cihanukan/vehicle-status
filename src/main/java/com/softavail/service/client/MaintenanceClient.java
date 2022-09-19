package com.softavail.service.client;

import com.softavail.common.exception.VehicleStatusServerErrorException;
import com.softavail.common.exception.VehicleStatusServiceUnavailableException;
import com.softavail.dto.MaintenanceResponse;
import com.softavail.dto.VehicleStatusResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client(value = "${clients.vehicleStatus}")
public interface MaintenanceClient {

    @Get("/cars/{vin}")
    MaintenanceResponse getMaintenanceInfo(@PathVariable String vin) throws VehicleStatusServerErrorException;
}
