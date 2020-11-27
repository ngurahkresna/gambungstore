package com.gambungstore.buyer.models.promo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promo {

    @SerializedName("data")
    List<DataPromo> dataPromo;

    public List<DataPromo> getDataPromo() {
        return dataPromo;
    }

    public void setDataPromo(List<DataPromo> dataPromo) {
        this.dataPromo = dataPromo;
    }
}
