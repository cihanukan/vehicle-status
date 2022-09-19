package com.softavail.common.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MaintenanceType {
    @JsonProperty("Very_Low")
    VERY_LOW("Very_Low","poor"),
    @JsonProperty("Low")
    LOW("Low", "poor"),
    @JsonProperty("Medium")
    MEDIUM("Medium", "average"),
    @JsonProperty("High")
    HIGH("High", "high");

    private String name;
    private String score;


    public static String getEnumByString(String name){
        for(MaintenanceType e : MaintenanceType.values()){
            if(e.name.equalsIgnoreCase(name)) return e.score;
        }
        return null;
    }
}
