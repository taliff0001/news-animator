package com.tommyaliff.livenews;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;


public class RestfulHeadlines {

    String apiKey = "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";
    String newsUri = "https://newsapi.org/v2/top-headlines?country=us&category=entertainment&apiKey=5bbd80e0943d481e8fdcba763a5d23bb";
    NewsApiResponse newsApiResponse;
    String openAiResponseString;

    public void fetchHeadlines() {
        HttpRequest getRequest = null;
        try { HttpClient httpClient = HttpClient.newHttpClient();
            getRequest = HttpRequest.newBuilder().uri(new URI(newsUri)).build();

            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            newsApiResponse = gson.fromJson(getResponse.body(), NewsApiResponse.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    public FakeDAO postRequest(List<Article> articleList) throws IOException, URISyntaxException, InterruptedException {

        String nextArticle = articleList.remove(1).getTitle();

        if (nextArticle.contains("Removed"))
            return null;

        String prompt = "Today's News: " + nextArticle;

        openAiResponseString = "{\n" + "    \"prompt\": " + "\"" + prompt + "\"" + ",\n" + "    \"n\": 1,\n" + "    \"size\": \"512x512\"\n" + "  }";

        java.net.http.HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/images/generations"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(openAiResponseString))
                .build();

        System.out.println(openAiResponseString);

        java.net.http.HttpResponse<String> postResponse;
        try {java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            postResponse = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }

        System.out.println(postResponse.statusCode());
        System.out.println("Response: " + postResponse.body());

        Gson gson = new Gson();
        OpenAiResponse openAiResponse = gson.fromJson(postResponse.body(), OpenAiResponse.class);

        FakeDAO fakeDAO = new FakeDAO();
        fakeDAO.setFakeImageUrl(openAiResponse.getUrlList().get(0).getUrl());
        fakeDAO.setTitle(nextArticle);
        return fakeDAO;
    }


    public NewsApiResponse getApiResponse() {
        return newsApiResponse;
    }

    public void setApiResponse(NewsApiResponse newsApiResponse) {
        this.newsApiResponse = newsApiResponse;
    }

    public String getOpenAiResponseString() {
        return openAiResponseString;
    }

    public void setOpenAiResponseString(String openAiResponseString) {
        this.openAiResponseString = openAiResponseString;
    }
}
