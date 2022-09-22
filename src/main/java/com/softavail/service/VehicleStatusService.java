package com.softavail.service;

import com.softavail.common.enums.Feature;
import com.softavail.common.enums.MaintenanceType;
import com.softavail.common.exception.VehicleStatusServiceUnavailableErrorException;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.constant.ApplicationConstant;
import com.softavail.dto.InsuranceReportResponse;
import com.softavail.dto.MaintenanceResponse;
import com.softavail.dto.VehicleStatusRequest;
import com.softavail.dto.VehicleStatusResponse;
import com.softavail.service.client.InsuranceClient;
import com.softavail.service.client.MaintenanceClient;
import jakarta.inject.Singleton;
import org.apache.log4j.MDC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class VehicleStatusService {

    private final InsuranceClient insuranceClient;
    private final MaintenanceClient maintenanceClient;

    public VehicleStatusResponse getVehicleStatus(VehicleStatusRequest vehicleStatusRequest) throws VinNumberNotFoundException, VehicleStatusServiceUnavailableErrorException {
        List<Feature> features = vehicleStatusRequest.getFeatures();
        InsuranceReportResponse reportResponse = null;
        MaintenanceResponse maintenanceResponse= null;

        VehicleStatusResponse vehicleStatusResponse = new VehicleStatusResponse();

        //Api call for insurance client if feature contain "accident_free" property
        if (features.contains(Feature.ACCIDENT_FREE)){
            reportResponse = getInsuranceReport(vehicleStatusRequest.getVin());
            if (reportResponse == null)
                throw new VinNumberNotFoundException();
            else
                vehicleStatusResponse.setAccidentFree(reportResponse.getReport().getClaims() == 0);
        }

        //Api call for maintenance client if feature contain "maintenance" property
        if (features.contains(Feature.MAINTENANCE)){
            maintenanceResponse = getMaintenanceInfo(vehicleStatusRequest.getVin());
            if(maintenanceResponse == null)
                throw new VinNumberNotFoundException();
            else{
                String maintenanceScore = MaintenanceType.getEnumByString(maintenanceResponse.getMaintenanceFrequency());
                vehicleStatusResponse.setMaintenanceScore(maintenanceScore);
            }
        }
        vehicleStatusResponse.setRequestId((String) MDC.get(ApplicationConstant.REQUEST_ID));
        vehicleStatusResponse.setVin(vehicleStatusRequest.getVin());

        return vehicleStatusResponse;
    }

    private InsuranceReportResponse getInsuranceReport(String vin) throws VehicleStatusServiceUnavailableErrorException {
        InsuranceReportResponse reportResponse;
        try {
            reportResponse = insuranceClient.getInsuranceReport(vin);
        }
        catch (Exception e){
            throw new VehicleStatusServiceUnavailableErrorException(ApplicationConstant.INSURANCE_CALL_ERROR);
        }
        return reportResponse;
    }

    private MaintenanceResponse getMaintenanceInfo (String vin) throws VehicleStatusServiceUnavailableErrorException {
        MaintenanceResponse maintenanceResponse;
        try {
            maintenanceResponse = maintenanceClient.getMaintenanceInfo(vin);
        }
        catch (Exception e){
            throw new VehicleStatusServiceUnavailableErrorException(ApplicationConstant.MAINTENANCE_CALL_ERROR);
        }
        return maintenanceResponse;
    }

}
