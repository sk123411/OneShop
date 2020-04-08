package com.skewapps.app.oneshop.Model;

public class ProductsData {

    private String name;
    private String url;
    private String link;
    private String id;
    private String price;
    private String description;
    private String discount;
    private String Poskey;

    public ProductsData() {


    }

    public ProductsData(String name, String url, String link, String id, String price, String description, String discount, String poskey) {
        this.name = name;
        this.url = url;
        this.link = link;
        this.id = id;
        this.price = price;
        this.description = description;
        this.discount = discount;
        Poskey = poskey;
    }

    public String getPoskey() {
        return Poskey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setPoskey(String poskey) {
        Poskey = poskey;
    }
}
