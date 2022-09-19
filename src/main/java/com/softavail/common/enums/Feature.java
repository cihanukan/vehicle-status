package com.softavail.common.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Feature {
    @JsonProperty("accident_free")
    ACCIDENT_FREE,
    @JsonProperty("maintenance")
    MAINTENANCE

}
