package com.softavail.service;

import com.softavail.common.enums.Feature;
import com.softavail.common.enums.MaintenanceType;
import com.softavail.common.exception.VehicleStatusServiceUnavailableErrorException;
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

    private final Tracer tracer;
    public VehicleStatusResponse getVehicleStatus(VehicleStatusRequest vehicleStatusRequest) throws VinNumberNotFoundException, VehicleStatusServiceUnavailableErrorException {
        List<Feature> features = vehicleStatusRequest.getFeatures();
        InsuranceReportResponse reportResponse = null;

        log.debug(tracer.activeSpan().context().toTraceId());
        VehicleStatusResponse vehicleStatusResponse = new VehicleStatusResponse();
        if (features.contains(Feature.ACCIDENT_FREE))
            reportResponse = getInsuranceReport(vehicleStatusRequest.getVin());

        MaintenanceResponse maintenanceResponse = null;
        if (features.contains(Feature.MAINTENANCE)){
            maintenanceResponse = getMaintenanceInfo(vehicleStatusRequest.getVin());
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

    private InsuranceReportResponse getInsuranceReport(String vin) throws VehicleStatusServiceUnavailableErrorException {
        InsuranceReportResponse reportResponse;
        try {
            reportResponse = insuranceClient.getInsuranceReport(vin);
        }
        catch (Exception e){
            throw new VehicleStatusServiceUnavailableErrorException("Insurance service call error");
        }
        return reportResponse;
    }

    private MaintenanceResponse getMaintenanceInfo (String vin) throws VehicleStatusServiceUnavailableErrorException {
        MaintenanceResponse maintenanceResponse;
        try {
            maintenanceResponse = maintenanceClient.getMaintenanceInfo(vin);
        }
        catch (Exception e){
            throw new VehicleStatusServiceUnavailableErrorException("Maintenance service call error");
        }
        return maintenanceResponse;
    }

}
