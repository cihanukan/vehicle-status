package com.softavail.service;

import com.softavail.common.enums.Feature;
import com.softavail.common.enums.MaintenanceType;
import com.softavail.common.exception.VehicleStatusServerErrorException;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.dto.InsuranceReportResponse;
import com.softavail.dto.MaintenanceResponse;
import com.softavail.dto.VehicleStatusRequest;
import com.softavail.dto.VehicleStatusResponse;
import com.softavail.service.client.InsuranceClient;
import com.softavail.service.client.MaintenanceClient;
import io.opentracing.Tracer;
import jakarta.inject.Singleton;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class VehicleStatusService {

    private final InsuranceClient insuranceClient;
    private final MaintenanceClient maintenanceClient;

    public VehicleStatusResponse getVehicleStatus(VehicleStatusRequest vehicleStatusRequest) throws VinNumberNotFoundException, VehicleStatusServerErrorException {
        List<Feature> features = vehicleStatusRequest.getFeatures();
        InsuranceReportResponse reportResponse = null;
        MaintenanceResponse maintenanceResponse= null;

        VehicleStatusResponse vehicleStatusResponse = new VehicleStatusResponse();

        //Api call for insurance client if feature contain "accident_free" property
        if (features.contains(Feature.ACCIDENT_FREE))
            reportResponse = insuranceClient.getInsuranceReport(vehicleStatusRequest.getVin());

        //Api call for maintenance client if feature contain "maintenance" property
        if (features.contains(Feature.MAINTENANCE)){
            maintenanceResponse = maintenanceClient.getMaintenanceInfo(vehicleStatusRequest.getVin());
        }

        vehicleStatusResponse.setVin(vehicleStatusRequest.getVin());

        if (reportResponse == null)
            throw new VinNumberNotFoundException();
        else
            vehicleStatusResponse.setAccidentFree(reportResponse.getReport().getClaims() == 0);

        if(maintenanceResponse == null)
            throw new VinNumberNotFoundException();
        else{
            String maintenanceScore = MaintenanceType.getEnumByString(maintenanceResponse.getMaintenanceFrequency());
            vehicleStatusResponse.setMaintenanceScore(maintenanceScore);
        }

        return vehicleStatusResponse;
    }

}