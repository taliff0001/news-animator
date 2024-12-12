package com.tommyaliff.newsanimator;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.http.HttpClient.newHttpClient;


public class RestfulHeadlines {

    String apiKey = "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";
    String newsUri = "https://newsapi.org/v2/top-headlines?country=us&category=entertainment&apiKey=9d3213573321478e828a2b254752efe2";
    NewsApiResponse newsApiResponse;
    String openAiResponseString;

    public void fetchHeadlines() {
        HttpRequest getRequest = null;
        try { HttpClient httpClient = newHttpClient();
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
        try {java.net.http.HttpClient client = newHttpClient();
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
