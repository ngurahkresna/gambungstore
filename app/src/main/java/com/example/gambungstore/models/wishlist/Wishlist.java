package com.example.gambungstore.models.wishlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wishlist {

    @SerializedName("data")
    List<DataWishlist> dataWishlist;

    public List<DataWishlist> getDataWishlist() {
        return dataWishlist;
    }

    public void setDataWishlist(List<DataWishlist> dataWishlist) {
        this.dataWishlist = dataWishlist;
    }
}
