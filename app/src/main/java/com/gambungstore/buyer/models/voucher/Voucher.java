package com.gambungstore.buyer.models.voucher;

import com.google.gson.annotations.SerializedName;

public class Voucher {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("percentage")
    private int percentage;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
