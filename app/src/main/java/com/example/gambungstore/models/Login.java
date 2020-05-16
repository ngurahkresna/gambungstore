package com.example.gambungstore.models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("token")
    private String token;
    @SerializedName("email_verified_at")
    private String verified;

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
