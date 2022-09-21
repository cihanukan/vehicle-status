package com.softavail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.softavail.common.enums.Feature;
import com.softavail.common.exception.VehicleStatusServiceUnavailableErrorException;
import com.softavail.common.exception.VinNumberNotFoundException;
import com.softavail.dto.InsuranceReportResponse;
import com.softavail.dto.MaintenanceResponse;
import com.softavail.dto.Report;
import com.softavail.dto.VehicleStatusRequest;
import com.softavail.dto.VehicleStatusResponse;
import com.softavail.service.client.InsuranceClient;
import com.softavail.service.client.MaintenanceClient;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class VehicleStatusServiceTest {

    @Test
    void testGetVehicleStatus()
            throws VehicleStatusServiceUnavailableErrorException, VinNumberNotFoundException, InterruptedException {
        VehicleStatusService vehicleStatusService = new VehicleStatusService(mock(InsuranceClient.class),
                mock(MaintenanceClient.class));

        VehicleStatusRequest vehicleStatusRequest = new VehicleStatusRequest();
        vehicleStatusRequest.setFeatures(new ArrayList<>());
        vehicleStatusRequest.setVin("4Y1SL65848Z411439");
        VehicleStatusResponse actualVehicleStatus = vehicleStatusService.getVehicleStatus(vehicleStatusRequest);
        assertEquals("4Y1SL65848Z411439", actualVehicleStatus.getVin());
        assertNull(actualVehicleStatus.getRequestId());
    }

    @Test
    void testGetVehicleStatus2()
            throws VehicleStatusServiceUnavailableErrorException, VinNumberNotFoundException, InterruptedException {
        Report report = new Report();
        report.setClaims(3);

        InsuranceReportResponse insuranceReportResponse = new InsuranceReportResponse();
        insuranceReportResponse.setReport(report);
        InsuranceClient insuranceClient = mock(InsuranceClient.class);
        when(insuranceClient.getInsuranceReport((String) any())).thenReturn(insuranceReportResponse);
        VehicleStatusService vehicleStatusService = new VehicleStatusService(insuranceClient,
                mock(MaintenanceClient.class));

        ArrayList<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.ACCIDENT_FREE);

        VehicleStatusRequest vehicleStatusRequest = new VehicleStatusRequest();
        vehicleStatusRequest.setFeatures(featureList);
        vehicleStatusRequest.setVin("4Y1SL65848Z411439");
        VehicleStatusResponse actualVehicleStatus = vehicleStatusService.getVehicleStatus(vehicleStatusRequest);
        assertFalse(actualVehicleStatus.getAccidentFree());
        assertEquals("4Y1SL65848Z411439", actualVehicleStatus.getVin());
        assertNull(actualVehicleStatus.getRequestId());
        verify(insuranceClient).getInsuranceReport((String) any());
    }

    @Test
    void testGetVehicleStatus3()
            throws VehicleStatusServiceUnavailableErrorException, VinNumberNotFoundException, InterruptedException {
        Report report = new Report();
        report.setClaims(0);

        InsuranceReportResponse insuranceReportResponse = new InsuranceReportResponse();
        insuranceReportResponse.setReport(report);
        InsuranceClient insuranceClient = mock(InsuranceClient.class);
        when(insuranceClient.getInsuranceReport((String) any())).thenReturn(insuranceReportResponse);
        VehicleStatusService vehicleStatusService = new VehicleStatusService(insuranceClient,
                mock(MaintenanceClient.class));

        ArrayList<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.ACCIDENT_FREE);

        VehicleStatusRequest vehicleStatusRequest = new VehicleStatusRequest();
        vehicleStatusRequest.setFeatures(featureList);
        vehicleStatusRequest.setVin("4Y1SL65848Z411439");
        VehicleStatusResponse actualVehicleStatus = vehicleStatusService.getVehicleStatus(vehicleStatusRequest);
        assertTrue(actualVehicleStatus.getAccidentFree());
        assertEquals("4Y1SL65848Z411439", actualVehicleStatus.getVin());
        assertNull(actualVehicleStatus.getRequestId());
        verify(insuranceClient).getInsuranceReport((String) any());
    }

    @Test
    void testGetVehicleStatus4()
            throws VehicleStatusServiceUnavailableErrorException, VinNumberNotFoundException, InterruptedException {
        Report report = new Report();
        report.setClaims(3);

        InsuranceReportResponse insuranceReportResponse = new InsuranceReportResponse();
        insuranceReportResponse.setReport(report);
        InsuranceClient insuranceClient = mock(InsuranceClient.class);
        when(insuranceClient.getInsuranceReport((String) any())).thenReturn(insuranceReportResponse);

        MaintenanceResponse maintenanceResponse = new MaintenanceResponse();
        maintenanceResponse.setMaintenanceFrequency("average");
        MaintenanceClient maintenanceClient = mock(MaintenanceClient.class);
        when(maintenanceClient.getMaintenanceInfo((String) any())).thenReturn(maintenanceResponse);
        VehicleStatusService vehicleStatusService = new VehicleStatusService(insuranceClient, maintenanceClient);

        ArrayList<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.MAINTENANCE);

        VehicleStatusRequest vehicleStatusRequest = new VehicleStatusRequest();
        vehicleStatusRequest.setFeatures(featureList);
        vehicleStatusRequest.setVin("4Y1SL65848Z411439");
        VehicleStatusResponse actualVehicleStatus = vehicleStatusService.getVehicleStatus(vehicleStatusRequest);
        assertEquals("4Y1SL65848Z411439", actualVehicleStatus.getVin());
        assertNull(actualVehicleStatus.getRequestId());
        assertNull(actualVehicleStatus.getMaintenanceScore());
        verify(maintenanceClient).getMaintenanceInfo((String) any());
    }


    @Test
    void testGetVehicleStatus5()
            throws VehicleStatusServiceUnavailableErrorException, VinNumberNotFoundException, InterruptedException {
        Report report = new Report();
        report.setClaims(1);

        InsuranceReportResponse insuranceReportResponse = new InsuranceReportResponse();
        insuranceReportResponse.setReport(report);
        InsuranceClient insuranceClient = mock(InsuranceClient.class);
        when(insuranceClient.getInsuranceReport((String) any())).thenReturn(insuranceReportResponse);

        MaintenanceResponse maintenanceResponse = new MaintenanceResponse();
        maintenanceResponse.setMaintenanceFrequency("poor");
        MaintenanceClient maintenanceClient = mock(MaintenanceClient.class);
        when(maintenanceClient.getMaintenanceInfo((String) any())).thenReturn(maintenanceResponse);
        VehicleStatusService vehicleStatusService = new VehicleStatusService(insuranceClient, maintenanceClient);

        ArrayList<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.MAINTENANCE);

        VehicleStatusRequest vehicleStatusRequest = new VehicleStatusRequest();
        vehicleStatusRequest.setFeatures(featureList);
        vehicleStatusRequest.setVin("4Y1SL65848Z411439");
        VehicleStatusResponse actualVehicleStatus = vehicleStatusService.getVehicleStatus(vehicleStatusRequest);
        assertEquals("4Y1SL65848Z411439", actualVehicleStatus.getVin());
        assertNull(actualVehicleStatus.getRequestId());
        assertNull(actualVehicleStatus.getMaintenanceScore());
        verify(maintenanceClient).getMaintenanceInfo((String) any());
    }
}

