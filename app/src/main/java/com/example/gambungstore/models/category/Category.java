package com.example.gambungstore.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("data")
    private List<DataCategory> categories;

    public List<DataCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<DataCategory> categories) {
        this.categories = categories;
    }
}
