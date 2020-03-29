package com.example.gambungstore.models.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gambungstore.models.cart.DataCart;
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
    @SerializedName("weight")
    private int weight;
    @SerializedName("stock")
    private int stock;
    @SerializedName("cart")
    private List<DataCart> carts;
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

    public List<DataCart> getCarts() {
        return carts;
    }

    public void setCarts(List<DataCart> carts) {
        this.carts = carts;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
