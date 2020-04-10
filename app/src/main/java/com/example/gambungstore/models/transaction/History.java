package com.example.gambungstore.models.transaction;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
