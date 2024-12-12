package com.tommyaliff.newsanimator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RestfulHeadlines {
    private static final String API_KEY = System.getenv("STABILITY_API_KEY");
    private static final String API_URL = "https://api.stability.ai/v1/generation/stable-diffusion-v1-6/text-to-image";
    private static final String NEWS_API_KEY = System.getenv("NEWS_API_KEY");
    private final String newsUri;
    private NewsApiResponse newsApiResponse;
    private final Gson gson;

    public RestfulHeadlines() {
        this.newsUri = String.format(
                "https://newsapi.org/v2/top-headlines?country=us&category=entertainment&apiKey=%s",
                NEWS_API_KEY
        );
        this.gson = new Gson();
    }

    public void fetchHeadlines() {
        HttpRequest getRequest;
        try {
            HttpResponse<String> getResponse;
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                getRequest = HttpRequest.newBuilder()
                        .uri(new URI(newsUri))
                        .GET()
                        .build();

                getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            }

            if (getResponse.statusCode() != 200) {
                System.err.println("News API error: " + getResponse.statusCode() + " - " + getResponse.body());
                return;
            }

            try {
                newsApiResponse = gson.fromJson(getResponse.body(), NewsApiResponse.class);
                if (newsApiResponse == null || newsApiResponse.getArticles() == null) {
                    System.err.println("Invalid response format from News API");
                }
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing News API response: " + e.getMessage());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.err.println("Error fetching headlines: " + e.getMessage());
        }
    }

    public FakeDAO postRequest(List<Article> articleList) throws IOException, InterruptedException {
        if (articleList == null || articleList.isEmpty() || articleList.size() < 2) {
            return null;
        }

        String nextArticle = articleList.remove(1).getTitle();
        if (nextArticle == null || nextArticle.isEmpty() || nextArticle.contains("Removed")) {
            return null;
        }

        String requestBody = String.format(
                "{\n  \"cfg_scale\": 7,\n  \"clip_guidance_preset\": \"NONE\",\n  \"height\": 512,\n  \"width\": 512,\n  \"samples\": 1,\n  \"seed\": 0,\n  \"steps\": 30,\n  \"text_prompts\": [\n    {\n      \"text\": \"%s\",\n      \"weight\": 1\n    }\n  ]\n}",
                nextArticle.replace("\"", "'") // Escape quotes in the title
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        if (response.statusCode() == 200) {
            try {
                StabilityResponse stabilityResponse = gson.fromJson(response.body(), StabilityResponse.class);
                if (stabilityResponse == null ||
                        stabilityResponse.artifacts == null ||
                        stabilityResponse.artifacts.length == 0 ||
                        stabilityResponse.artifacts[0] == null ||
                        stabilityResponse.artifacts[0].base64 == null) {
                    System.err.println("Invalid response format from Stability API");
                    return null;
                }

                FakeDAO fakeDAO = new FakeDAO();
                fakeDAO.setFakeImageUrl(stabilityResponse.artifacts[0].base64);
                fakeDAO.setTitle(nextArticle);
                return fakeDAO;
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing Stability API response: " + e.getMessage());
                return null;
            }
        } else {
            System.err.println("Stability API error: " + response.statusCode() + " - " + response.body());
            return null;
        }
    }

    public NewsApiResponse getApiResponse() {
        return newsApiResponse;
    }
}