package org.spring.service;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.model.City;
import org.spring.model.CityListResponse;
import org.spring.util.HttpUtil;

import java.util.*;

public class CityService {
    private static final String CITY_URL =
            "http://weather-automation-checkpoint-task.westeurope.cloudapp.azure.com:3000/cities";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<String> fetchAndValidateCities() throws Exception {
        String json = HttpUtil.get(CITY_URL);

        CityListResponse cityList = mapper.readValue(json, CityListResponse.class);

        Set<String> uniqueValidCities = new LinkedHashSet<>();
        for (City city : cityList.getCities()) {
            if (city.getName() != null && !city.getName().trim().isEmpty()) {
                uniqueValidCities.add(city.getName().trim());
            }
        }

        if (uniqueValidCities.isEmpty()) {
            throw new IllegalStateException("No valid cities found.");
        }

        return new ArrayList<>(uniqueValidCities);
    }
}

