package com.tommyaliff.newsanimator;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Model {
    private final RestfulHeadlines restfulHeadlines;
    private final Image defaultImage;
    private String fakeTitle;
    private final List<Article> articleList = new ArrayList<>();
    protected int index = 0;

    public Model() {
        restfulHeadlines = new RestfulHeadlines();
        defaultImage = initializeDefaultImage();
        initializeArticles();
    }

    private void initializeArticles() {
        restfulHeadlines.fetchHeadlines();
        NewsApiResponse response = restfulHeadlines.getApiResponse();
        if (response != null && response.getArticles() != null) {
            articleList.addAll(response.getArticles());
        }
    }

    private Image initializeDefaultImage() {
        InputStream defaultStream = getClass().getResourceAsStream("/com/tommyaliff/newsanimator/nothing.png");
        if (defaultStream == null) {
            System.err.println("Warning: Default image resource not found");
            // Create a tiny blank image as fallback
            return new Image(new ByteArrayInputStream(new byte[0]), 1, 1, true, true);
        }
        return new Image(defaultStream);
    }

    public String getNewsTitle() {
        if (isValidArticleIndex()) {
            return "No Articles Found";
        }
        Article currentArticle = articleList.get(index);
        if (currentArticle.getTitle() == null || currentArticle.getTitle().equals("[Removed]")) {
            return "Nothing to see here!!";
        }
        return currentArticle.getTitle();
    }

    public String getNewsDescription() {
        if (isValidArticleIndex()) {
            return "No Articles Found";
        }
        Article currentArticle = articleList.get(index);
        if (currentArticle.getDescription() == null || currentArticle.getTitle().equals("[Removed]")) {
            return "Nothing to see here!!";
        }
        return currentArticle.getDescription();
    }

    public Image getFakeNews() {
        FakeDAO fake = null;
        try {
            if (!articleList.isEmpty()) {
                fake = restfulHeadlines.postRequest(articleList);
            }
        } catch (Exception e) {
            System.err.println("Error generating fake news: " + e.getMessage());
            e.printStackTrace();
        }

        if (fake == null || fake.getFakeImageUrl() == null) {
            return defaultImage;
        }

        try {
            fakeTitle = fake.getTitle();
            byte[] imageBytes = Base64.getDecoder().decode(fake.getFakeImageUrl());
            return new Image(new ByteArrayInputStream(imageBytes));
        } catch (IllegalArgumentException e) {
            System.err.println("Error decoding image: " + e.getMessage());
            return defaultImage;
        }
    }

    public String getFakeTitle() {
        return fakeTitle != null ? fakeTitle : "Failed to generate fake news";
    }

    public Image getNewsImage() {
        if (isValidArticleIndex()) {
            return defaultImage;
        }

        Article currentArticle = articleList.get(index);
        String imageUrl = currentArticle.getUrlToImage();

        if (currentArticle.getTitle().equals("[Removed]") || imageUrl == null || imageUrl.isEmpty()) {
            return defaultImage;
        }

        try {
            return new Image(imageUrl, 600, 0, true, true);
        } catch (Exception e) {
            System.err.println("Error loading image from URL: " + imageUrl);
            return defaultImage;
        }
    }

    private boolean isValidArticleIndex() {
        return articleList.isEmpty() || index >= articleList.size();
    }
}