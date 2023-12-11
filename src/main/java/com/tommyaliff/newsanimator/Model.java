package com.tommyaliff.livenews;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private final RestfulHeadlines restfulHeadlines;
    private final Image dImage;
    private String fakeTitle;
    private static final String defaultImageLoc = "src/main/resources/com/tommyaliff/livenews/nothing.png";
    private final List<Article> articleList = new ArrayList<>();
    protected int index = 0;

    public Model() {

        restfulHeadlines = new RestfulHeadlines();
        restfulHeadlines.fetchHeadlines();
        NewsApiResponse response = restfulHeadlines.getApiResponse();
        articleList.addAll(response.getArticles());
        dImage = initializeDefaultImage();

    }

    private Image initializeDefaultImage() {
        InputStream defaultStream = null;
        try {
            defaultStream = new FileInputStream(defaultImageLoc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Image(defaultStream);
    }

    public String getNewsTitle() {
        if ((articleList.get(index).getTitle().equals("[Removed]") && index < articleList.size())) {
            return "Nothing to see here!!";
        } else if (index < articleList.size()){
            return articleList.get(index).getDescription();
        } else return "No Articles Found";
    }

    public String getNewsDescription() {
        if ((articleList.get(index).getTitle().equals("[Removed]") && index < articleList.size())) {
            return "Nothing to see here!!";
        } else if (index < articleList.size()) {
            return articleList.get(index).getDescription();
        } else return "No Articles Found";
    }

    public Image getFakeNews() {
        FakeDAO fake = null;
        try {
            fake = restfulHeadlines.postRequest(articleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } if(fake == null)
            return dImage;
        else {
            fakeTitle = fake.getTitle();
            return new Image(fake.fakeImageUrl);
        }
    }

    public String getFakeTitle() {
        return fakeTitle;
    }

    public Image getNewsImage() {
        if (index < articleList.size()) {
            if (articleList.get(index).getTitle().equals("[Removed]") || (articleList.get(index).getUrlToImage() == null)) {
                return dImage;
            }
            return new Image(articleList.get(index).getUrlToImage(), 600, 0, true, true);
        } else {
            return dImage;
        }
    }
}
