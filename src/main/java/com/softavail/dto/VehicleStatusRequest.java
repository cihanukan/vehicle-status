package com.softavail.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

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

    @NotEmpty(message = "Possible values: \"accident_free\", \"maintenance\". At least one feature must be provided")
    private ArrayList<String> features;

}
