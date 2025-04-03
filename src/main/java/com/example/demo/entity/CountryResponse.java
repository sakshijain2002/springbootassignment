package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@Data
public class CountryResponse {
    private Name name;
    private List<String> capital;
    private Map<String, String> languages;
    private String region;
    private Long population;
    private Flags flags;

	@Data
public static class Name {
    private String common;


    
}

@Data
public static class Flags {
    private String png;
    
}
}

