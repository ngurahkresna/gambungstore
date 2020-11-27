package com.gambungstore.buyer.models.store;

import com.gambungstore.buyer.models.checkout.Expedition;
import com.gambungstore.buyer.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataStore {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("username")
    private String username;
    @SerializedName("description")
    private String description;
    @SerializedName("address_1")
    private String address;
    @SerializedName("phone_1")
    private String phone;
    @SerializedName("city")
    private String city;
    @SerializedName("expedition")
    private List<Expedition> expeditions;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Expedition> getExpeditions() {
        return expeditions;
    }

    public void setExpeditions(List<Expedition> expeditions) {
        this.expeditions = expeditions;
    }

    public List<DataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DataProduct> products) {
        this.products = products;
    }
}
