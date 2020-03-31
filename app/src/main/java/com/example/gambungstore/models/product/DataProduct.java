package com.example.gambungstore.models.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gambungstore.models.Store;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataProduct implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("store_code")
    private String store_code;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("stock")
    private int stock;
    @SerializedName("wish_list_count")
    private int wish_list_count;
    @SerializedName("transaction_count")
    private int transaction_count;
    @SerializedName("store")
    private Store store;
    @SerializedName("images")
    private List<ProductImage> images;

    protected DataProduct(Parcel in) {
        id = in.readInt();
        code = in.readString();
        store_code = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readInt();
    }

    public static final Creator<DataProduct> CREATOR = new Creator<DataProduct>() {
        @Override
        public DataProduct createFromParcel(Parcel in) {
            return new DataProduct(in);
        }

        @Override
        public DataProduct[] newArray(int size) {
            return new DataProduct[size];
        }
    };

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

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getWishListCount() {
        return wish_list_count;
    }

    public void setWishListCount(int wish_list_count) {
        this.wish_list_count = wish_list_count;
    }

    public int getTransactionCount() {
        return transaction_count;
    }

    public void setTransactionCount(int transaction_count) {
        this.transaction_count = transaction_count;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(store_code);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(price);
    }
}
