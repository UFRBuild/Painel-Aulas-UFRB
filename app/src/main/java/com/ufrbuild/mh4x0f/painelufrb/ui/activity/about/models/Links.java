package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models;

public class Links {
    private String title;
    private String url;
    private String img_path;

    public Links(String title, String url, String image){
        setTitle(title);
        setUrl(url);
        setImg_path(image);
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
