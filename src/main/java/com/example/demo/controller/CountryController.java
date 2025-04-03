package com.example.demo.controller;

import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
@Tag(name = "Country Controller", description = "Controller for performing crud operations on country data")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }


    @GetMapping("/pagination")
    public List<Country> getPaginatedCountries(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        return countryService.getPaginatedCountries(page, size).getContent();
    }
    //get country by id
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @PostMapping("/add")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }

    @PutMapping("/update/{id}")
    public Country updateCountry(@PathVariable Long id,@RequestBody Country country){
        return countryService.updateCountry(id,country);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id){
        countryService.deleteCountry(id);
        return ResponseEntity.ok("country deleted successfully");

    }


}
