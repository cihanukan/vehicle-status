package com.softavail.common.exception;

import com.softavail.common.enums.VehicleStatusErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class VehicleStatusException extends Exception {

    private final VehicleStatusErrorCode errorCode;
    private final String errorDescription;
    public VehicleStatusException(VehicleStatusErrorCode errorCode, String errorDescription) {
        super(errorCode.name() + ":" + errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
