package com.example.demo.service;

import com.example.demo.entity.Country;
import com.example.demo.entity.CountryDTO;
import com.example.demo.entity.CountryResponse;
import com.example.demo.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;

    public ExternalApiService(RestTemplate restTemplate, ModelMapper modelMapper, CountryRepository countryRepository) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
    }


    public List<CountryDTO> fetchCountriesFromApi() {
        String url = "https://restcountries.com/v3.1/all";

        try {
            ResponseEntity<CountryResponse[]> responseEntity = restTemplate.getForEntity(url, CountryResponse[].class);
            CountryResponse[] response = responseEntity.getBody();

            if (response == null || response.length == 0) {
                return List.of(); // Return an empty list if no data is found
            }

            // Convert API response to DTOs
            return Arrays.stream(response)
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error fetching countries from API: " + e.getMessage());
            return List.of(); // Return an empty list in case of failure
        }
    }


    // Save API data into the database
    public void saveCountriesToDatabase() {
        List<CountryDTO> countryDTOs = fetchCountriesFromApi();

        List<Country> countryEntities = countryDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        countryRepository.saveAll(countryEntities);
    }

    // Convert API response to CountryDTO
    private CountryDTO mapToDTO(CountryResponse country) {
        CountryDTO dto = modelMapper.map(country, CountryDTO.class);
        dto.setCapital(country.getCapital() != null ? country.getCapital().get(0) : "Unknown");
        dto.setLanguages(country.getLanguages() != null ? country.getLanguages().values().stream().toList() : List.of());
        if (country.getFlags() != null && country.getFlags().getPng() != null) {
            dto.setFlag(country.getFlags().getPng());
        } else {
            dto.setFlag("No flag available");
            System.err.println("Missing flag for country: " + dto.getName());
        }

        return dto;
    }

    // Convert DTO to Entity (for saving in the database)
    private Country mapToEntity(CountryDTO dto) {
        Country country = new Country();
        country.setName(dto.getName());
        country.setCapital(dto.getCapital());
        country.setLanguages(String.join(", ", dto.getLanguages()));
        country.setRegion(dto.getRegion());
        country.setPopulation(dto.getPopulation());
        country.setFlag(dto.getFlag());
        return country;
    }


}
