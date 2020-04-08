package com.skewapps.app.oneshop.Model;

import java.util.List;

public class ConfirmOrders {


    private String phone;


    private String name;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private String price;
    private List<Order> orders;
    private String total;
    private String status;
    private String image;
    private String PaymentState;
    private String courier;
    private String tracking;

    public ConfirmOrders() {
    }







    public ConfirmOrders(String phone, String name, String address, String pincode, String city, String state, String country,String price, List<Order> orders,
                         String status,String courier,String tracking) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.price = price;
        this.orders = orders;
        this.status = status;
        this.courier = courier;
        this.tracking = tracking;
    }







    public String getPaymentState() {
        return PaymentState;
    }

    public void setPaymentState(String paymentState) {
        PaymentState = paymentState;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getPrice() {
        return price;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCourier() {
        return courier;
    }

    public String getTracking() {
        return tracking;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }
}
