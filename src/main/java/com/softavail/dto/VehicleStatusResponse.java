package com.softavail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleStatusResponse {

    @JsonProperty("request_id")
    private String requestId;
    private String vin;
    @JsonProperty("accident_free")
    private Boolean accidentFree;
    @JsonProperty("maintenance_score")
    private String maintenanceScore;

}
