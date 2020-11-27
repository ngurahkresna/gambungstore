package com.gambungstore.buyer.models.promo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataPromo implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("percentage")
    private int percentage;
    @SerializedName("terms")
    private String terms;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;

    protected DataPromo(Parcel in) {
        id = in.readInt();
        code = in.readString();
        type = in.readString();
        percentage = in.readInt();
        terms = in.readString();
        start_date = in.readString();
        end_date = in.readString();
    }

    public static final Creator<DataPromo> CREATOR = new Creator<DataPromo>() {
        @Override
        public DataPromo createFromParcel(Parcel in) {
            return new DataPromo(in);
        }

        @Override
        public DataPromo[] newArray(int size) {
            return new DataPromo[size];
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

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(type);
        dest.writeInt(percentage);
        dest.writeString(terms);
        dest.writeString(start_date);
        dest.writeString(end_date);
    }
}
