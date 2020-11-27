package com.gambungstore.buyer.models;

import com.google.gson.annotations.SerializedName;

public class StatusOngkir {

    @SerializedName("code")
    private String code;
    @SerializedName("description")
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
