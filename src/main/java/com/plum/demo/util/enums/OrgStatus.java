package com.plum.demo.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OrgStatus {
    LIVE("live"),
    BLOCKED("blocked");

    @JsonValue
    private final String name;

    private static final Map<String, OrgStatus> lookUpMap = new HashMap<>();

    static {
        for (OrgStatus orgStatus : OrgStatus.values()) {
            lookUpMap.put(orgStatus.getName(), orgStatus);
        }
    }
}
