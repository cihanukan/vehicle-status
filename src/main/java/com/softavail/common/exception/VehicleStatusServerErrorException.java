package com.softavail.common.exception;

import com.softavail.common.enums.VehicleStatusErrorCode;

public class VehicleStatusServerErrorException extends VehicleStatusException{

    public VehicleStatusServerErrorException(String errorDescription) {
        super(VehicleStatusErrorCode.SERVER_ERROR.SERVER_ERROR, errorDescription);
    }
}
