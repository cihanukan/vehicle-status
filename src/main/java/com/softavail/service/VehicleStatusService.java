package com.softavail.service;

import com.softavail.common.enums.MaintenanceType;
import com.softavail.common.exception.VehicleStatusServerErrorException;
import com.softavail.common.exception.VehicleStatusServiceUnavailableException;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.dto.InsuranceReportResponse;
import com.softavail.dto.MaintenanceResponse;
import com.softavail.dto.VehicleStatusRequest;
import com.softavail.dto.VehicleStatusResponse;
import com.softavail.service.client.InsuranceClient;
import com.softavail.service.client.MaintenanceClient;
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
        List<String> features = vehicleStatusRequest.getFeatures();
        InsuranceReportResponse reportResponse = null;

        VehicleStatusResponse vehicleStatusResponse = new VehicleStatusResponse();
        if (features.contains("accident_free")) {
            reportResponse = insuranceClient.getInsuranceReport(vehicleStatusRequest.getVin());
        }
        MaintenanceResponse maintenanceResponse= null;
        if (features.contains("maintenance")){
            maintenanceResponse = maintenanceClient.getMaintenanceInfo(vehicleStatusRequest.getVin());
        }

        vehicleStatusResponse.setVin(vehicleStatusRequest.getVin());

        if (reportResponse == null) throw new VinNumberNotFoundException();

        vehicleStatusResponse.setAccidentFree(reportResponse.getReport().getClaims() == 0);
        if(maintenanceResponse != null){
            String maintenanceScore = MaintenanceType.getEnumByString(maintenanceResponse.getMaintenanceFrequency());
            vehicleStatusResponse.setMaintenanceScore(maintenanceScore);
        }

        return vehicleStatusResponse;
    }

}
