package com.softavail.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MaintenanceType {
    VERY_LOW("Very_Low","poor"),
    LOW("Low", "poor"),
    MEDIUM("Medium", "average"),
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
