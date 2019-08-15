package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models;

public class Developer {
    private String name;
    private String github;
    private String instagram;
    private String commits;
    private String image_link;



    public Developer(String name,String image, String instagram, String git){
        setName(name);
        setInstagram(instagram);
        setGithub(git);
        setImage_link(image);
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String facebook) {
        this.instagram = facebook;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }
}
