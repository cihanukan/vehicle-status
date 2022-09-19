package com.softavail.common.exception;

import com.softavail.common.enums.VehicleStatusErrorCode;

public class VinNumberNotFoundException extends VehicleStatusException {
    public VinNumberNotFoundException() {
        super(VehicleStatusErrorCode.VIN_NOT_FOUND, "VIN Number Not Found");
    }

}
