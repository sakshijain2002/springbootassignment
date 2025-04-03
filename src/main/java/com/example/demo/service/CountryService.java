package com.example.demo.service;

import com.example.demo.entity.Country;
import com.example.demo.entity.User;
import com.example.demo.exception.CountryNotFoundException;
import com.example.demo.repository.CountryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;



    public CountryService(CountryRepository countryRepository,ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Page<Country> getPaginatedCountries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Correctly creating Pageable
        return countryRepository.findAll(pageable);  // Ensure this is being used
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country with ID " + id + " not found"));
    }

    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Long id, Country record) {
        Country countryRecord = countryRepository.findById(id).orElseThrow(()->new CountryNotFoundException("Country with ID " + id + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(record, countryRecord);

        return countryRepository.save(countryRecord);
    }

    public void deleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException("Country with ID " + id + " not found");
        }
        countryRepository.deleteById(id);
    }


}
