package com.skewapps.app.oneshop.Model;

public class Member {

    private String link;
    private  String name;

    public Member() {
    }

    public Member(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
