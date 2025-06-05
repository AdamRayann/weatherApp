package org.spring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.util.HttpUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherService {
    private static final String KEY_URL =
            "http://weather-automation-checkpoint-task.westeurope.cloudapp.azure.com:3000//privateKey_shh";

    private String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherService() throws Exception {
        this.apiKey = fetchApiKey();
    }

    private String fetchApiKey() throws Exception {
        String keyJson = HttpUtil.get(KEY_URL);

        int start = keyJson.indexOf(":\"") + 2;
        int end = keyJson.indexOf("\"", start);
        return keyJson.substring(start, end);
    }




    public Double getTemperatureForCity(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                encodedCity, apiKey
        );


        String json = HttpUtil.get(url);
        JsonNode root = mapper.readTree(json);
        return root.path("main").path("temp").asDouble();
    }
}

