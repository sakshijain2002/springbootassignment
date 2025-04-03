package com.example.demo.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Country;
import com.example.demo.entity.CountryDTO;
import com.example.demo.repository.CountryRepository;
import com.example.demo.service.ExternalApiService;

@RestController
@RequestMapping("/external/countries")
@Tag(name = "External Api Controller", description = "Controller for fetching data from external api ")
public class ExternalApiController {

    private final ExternalApiService countryService;


    public ExternalApiController(ExternalApiService countryService) {
        this.countryService = countryService;

    }

    //fetch and save countries
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveCountries() {
        try {
            countryService.saveCountriesToDatabase();
            return ResponseEntity.ok("Countries fetched and saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch countries: " + e.getMessage());
        }
    }





}
