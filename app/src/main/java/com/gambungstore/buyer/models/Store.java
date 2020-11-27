package com.gambungstore.buyer.models;

import com.gambungstore.buyer.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Store {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("description")
    private String description;
    @SerializedName("address_1")
    private String address_1;
    @SerializedName("address_2")
    private String address_2;
    @SerializedName("address_3")
    private String address_3;
    @SerializedName("phone_1")
    private String phone_1;
    @SerializedName("phone_2")
    private String phone_2;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("users")
    private Profile user;
    @SerializedName("product")
    private List<DataProduct> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress1() {
        return address_1;
    }

    public void setAddress1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress2() {
        return address_2;
    }

    public void setAddress2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress3() {
        return address_3;
    }

    public void setAddress3(String address_3) {
        this.address_3 = address_3;
    }

    public String getPhone1() {
        return phone_1;
    }

    public void setPhone1(String phone_1) {
        this.phone_1 = phone_1;
    }

    public String getPhone2() {
        return phone_2;
    }

    public void setPhone2(String phone_2) {
        this.phone_2 = phone_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return getAddress1() + " " + getCity();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public List<DataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DataProduct> products) {
        this.products = products;
    }
}
