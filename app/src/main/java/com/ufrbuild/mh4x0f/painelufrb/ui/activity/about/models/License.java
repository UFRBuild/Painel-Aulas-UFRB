package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models;

public class License {
    private String author;
    private String name;
    private String description;
    private String url;


    public License(String name,String author, String description, String url){
        setAuthor(author);
        setName(name);
        setDescription(description);
        setUrl(url);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
