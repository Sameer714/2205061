package com.example.AverageCal.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class NumService {

    private final WebClient webClient;
    private static final String BASE_URL = "http://20.244.56.144/evaluation-service";
    private static final String AUTH_URL = BASE_URL + "/auth";
    private static final String PRIMES_URL = BASE_URL + "/primes";

    public NumService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    private String fetchToken() {
        try {
            Map<String, Object> response = webClient.post()
                    .uri(AUTH_URL)
                    .bodyValue(Map.of(
                        "email", "2205061@kiit.ac.in",
                        "name", "Sameer Purohit",
                        "rollNo", "2205061",
                        "accessCode", "nwpwrZ",
                        "clientID", "8c159b5d-d6f9-410d-b258-6463a0bd985e",
                        "clientSecret", "VJjHVNbhwpAgxsJm"
                    ))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("access_token")) {
                return "Bearer " + response.get("access_token").toString();
            } else {
                throw new RuntimeException("Failed to fetch token: Response is null or missing 'access_token' key");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching token: " + e.getMessage(), e);
        }
    }

    public String getPrimesFromExternalApi() {
        String token = fetchToken(); // Get new token before making request

        return webClient.get()
                .uri(PRIMES_URL)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String processRequest(String id) {
        switch (id.toLowerCase()) {
            case "p":
                return getPrimesFromExternalApi();
            default:
                return "Invalid ID!";
        }
    }
}
