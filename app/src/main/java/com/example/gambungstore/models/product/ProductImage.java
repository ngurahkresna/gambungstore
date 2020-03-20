package com.example.gambungstore.models.product;

import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("id")
    private int id;
    @SerializedName("image_name")
    private String image_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
