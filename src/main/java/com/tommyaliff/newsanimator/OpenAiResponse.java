package com.tommyaliff.livenews;

import java.util.List;

public class OpenAiResponse {

    public long created;

    public List<ImageURL> data;

    @Override
    public String toString() {
        return "OpenAiResponse{" +
                "created=" + created +
                ", urlList=" + data +
                '}';
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public List<ImageURL> getUrlList() {
        return data;
    }

    public void setURL(List<ImageURL> data) {
        this.data = data;
    }

}
