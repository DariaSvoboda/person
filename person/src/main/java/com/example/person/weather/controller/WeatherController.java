package com.example.person.weather.controller;

import com.example.person.weather.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Weather getWeather(@RequestParam double lat, @RequestParam double lon) {
        String key = "5c88bddfa67a48d9cd25674a7685e3dd";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + key + "&units=metric";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weather = (List<Map<String, Object>>) response.get("weather");

        return new Weather(
                Double.parseDouble(main.get("temp").toString()),
                weather.get(0).get("description").toString()
        );
    }
}