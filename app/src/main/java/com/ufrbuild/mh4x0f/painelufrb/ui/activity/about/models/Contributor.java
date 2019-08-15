package com.ufrbuild.mh4x0f.painelufrb.ui.activity.about.models;

public class Contributor {
    private String name;
    private String github;
    private String commits;
    private String image_link;

    public Contributor(String name,String image ,String git){
        setName(name);
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

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }
}
