package com.softavail.controller;

import com.softavail.common.exception.VehicleStatusServiceUnavailableErrorException;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.dto.VehicleStatusRequest;
import com.softavail.dto.VehicleStatusResponse;
import com.softavail.service.VehicleStatusService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@Controller("/vehicleStatus")
@RequiredArgsConstructor
public class VehicleStatusController {

    @Inject
    private final VehicleStatusService vehicleStatusService;

    @Operation(description = "Get Vehicle Status")
    @Post(uri = "/")
    public HttpResponse<VehicleStatusResponse> getVehicleStatus(@Valid @Body VehicleStatusRequest vehicleStatusRequest) throws VinNumberNotFoundException, VehicleStatusServiceUnavailableErrorException {
        return HttpResponse.ok(vehicleStatusService.getVehicleStatus(vehicleStatusRequest));
    }
}