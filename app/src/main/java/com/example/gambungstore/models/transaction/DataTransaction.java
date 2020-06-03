package com.example.gambungstore.models.transaction;

import com.example.gambungstore.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

public class DataTransaction {

    @SerializedName("id")
    private int id;
    @SerializedName("transaction_code")
    private String code;
    @SerializedName("expedition")
    private String expedition;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private int price;
    @SerializedName("shipping_status")
    private String shipping_status;
    @SerializedName("shipping_no")
    private String shipping_no;
    @SerializedName("created_at")
    private String tanggal;
    @SerializedName("transaction")
    private DetailTransaction detailTransaction;
    @SerializedName("product")
    private DataProduct product;
    @SerializedName("status")
    private String status;

    public String getExpedition() {
        return expedition;
    }

    public void setExpedition(String expedition) {
        this.expedition = expedition;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getShipping_no() {
        return shipping_no;
    }

    public void setShipping_no(String shipping_no) {
        this.shipping_no = shipping_no;
    }

    public DetailTransaction getDetailTransaction() {
        return detailTransaction;
    }

    public void setDetailTransaction(DetailTransaction detailTransaction) {
        this.detailTransaction = detailTransaction;
    }

    public DataProduct getProduct() {
        return product;
    }

    public void setProduct(DataProduct product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
