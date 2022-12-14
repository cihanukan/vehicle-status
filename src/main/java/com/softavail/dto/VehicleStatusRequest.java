package com.softavail.dto;

import com.softavail.common.enums.Feature;
import com.softavail.common.enums.MaintenanceType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.validation.Validated;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@Introspected
public class VehicleStatusRequest {

    @NotNull(message = "VIN number cannot be null")
    @NotEmpty(message = "VIN number cannot be empty")
    private String vin;

    //Enum used for possible values. If feature not exist in enum definition then throw exception
    //Tried to put custom validator but micronaut did not invoke when request received
    @NotEmpty(message = "Possible values: \"accident_free\", \"maintenance\". At least one feature must be provided")
    private ArrayList<Feature> features;

}

