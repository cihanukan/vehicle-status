package com.softavail.common.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VehicleStatusException {

    VIN_NOT_FOUND(100000L,"vin_number={vin} not found");

    private final Long errorCode;
    private final String errorMessage;


}
