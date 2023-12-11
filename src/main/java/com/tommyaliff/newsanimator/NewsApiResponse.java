package com.tommyaliff.livenews;

import java.util.List;

public class NewsApiResponse {
    public String status;
    public int totalResults;
    public List<Article> articles;



    public String articlesString(){
        String articleString = "";
        for(Article article : articles){
            articleString += article.toString() + "\n";
        }
        return articleString;
    }


    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

