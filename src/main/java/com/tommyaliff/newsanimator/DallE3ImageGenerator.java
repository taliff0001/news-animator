package com.tommyaliff.newsanimator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DallE3ImageGenerator implements ImageGenerationService {

    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    private static final String API_URL = "https://api.openai.com/v1/images/generations";

    public String generateImage(String prompt) throws Exception {
        String requestBody = String.format("{\"prompt\": \"%s\", \"n\": 1, \"size\": \"512x512\"}", prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return extractImageUrl(response.body());
    }

    private String extractImageUrl(String responseBody) {
        // parse response JSON and return the generated image URL
        return responseBody;
    }
}