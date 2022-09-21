package com.softavail.common.exception;

import com.softavail.common.enums.VehicleStatusErrorCode;
import com.softavail.constant.ApplicationConstant;

public class VinNumberNotFoundException extends VehicleStatusException {
    public VinNumberNotFoundException() {
        super(VehicleStatusErrorCode.VIN_NOT_FOUND, ApplicationConstant.VIN_NUMBER_NOT_FOUND);
    }

}
