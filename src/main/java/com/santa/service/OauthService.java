package com.santa.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class OauthService {
	
	public String searchUid(String token) {
		return token;
	}

	public String searchUidBkp(String token) {
		
        String apiUrl = System.getenv("OAUTH_API_URL");
        String apiSystem = System.getenv("OAUTH_API_SYSTEM");

        if (apiUrl == null || token == null) {
            throw new RuntimeException("Environment variables API_URL and API_TOKEN must be set.");
        }

        // Create HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Build request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", token)
                .header("System", apiSystem)
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            // Send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print response
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
		return "";
	}
}
