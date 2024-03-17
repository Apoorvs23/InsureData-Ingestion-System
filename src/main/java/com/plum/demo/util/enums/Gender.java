package com.plum.demo.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter // no setter required
@AllArgsConstructor // enum - male,age
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    @JsonValue
    private final String name;


    private static final Map<String, Gender> lookUpMap = new HashMap<>();

    static { //compiletime, one time execute
        for (Gender gender : Gender.values()) {
            lookUpMap.put(gender.getName(), gender); //getName - "male",MALE
        }
    }
 // lookupmap - 3 values
    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.ordinal();
    }

    public static Gender getGenderByName(String name) {
        if (Objects.isNull(name)) {
            return null;
        }
        return lookUpMap.get(name);
    }
}
