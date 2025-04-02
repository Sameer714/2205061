package com.example.AverageCal.Service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {
	
    private RestTemplate restTemplate;
    private static final String API_URL = "http://20.244.56.144/numbers/{id}";

    public List<Integer> fetchNumbers(String id) {
        try {
            ResponseEntity<Map<String, List<Integer>>> response =
                restTemplate.exchange(API_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, id);

            return response.getBody() != null ? response.getBody().get("numbers") : List.of();
        } catch (Exception e) {
            return List.of(); // Return empty list if API fails
        }
    }
}

