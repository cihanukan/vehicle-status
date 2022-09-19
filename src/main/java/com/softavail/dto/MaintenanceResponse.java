package com.softavail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceResponse {

    @JsonProperty("maintenance_frequency")
    private String maintenanceFrequency;

}
