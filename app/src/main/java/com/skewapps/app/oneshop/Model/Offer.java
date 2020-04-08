package com.skewapps.app.oneshop.Model;

public class Offer {

    private String image;
    private String title;
    private String description;
    private String url;
    private String steps;


    public Offer() {
    }

    public Offer(String title, String description, String url, String image,String steps) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.steps = steps;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

}
