package com.softavail.common.exception;

import com.softavail.common.enums.VehicleStatusErrorCode;

public class VehicleStatusServiceUnavailableErrorException extends VehicleStatusException{

    public VehicleStatusServiceUnavailableErrorException(String errorDescription) {
        super(VehicleStatusErrorCode.SERVICE_UNAVAILABLE, errorDescription);
    }
}
