package com.example.gambungstore.models;

public class LaporanDonasi {
    int img;
    String tujuanDonasi;
    String deskripsiDonasi;
    String jumlahDonasi;

    public LaporanDonasi(int img, String tujuanDonasi, String deskripsiDonasi, String jumlahDonasi) {
        this.img = img;
        this.tujuanDonasi = tujuanDonasi;
        this.deskripsiDonasi = deskripsiDonasi;
        this.jumlahDonasi = jumlahDonasi;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTujuanDonasi() {
        return tujuanDonasi;
    }

    public void setTujuanDonasi(String tujuanDonasi) {
        this.tujuanDonasi = tujuanDonasi;
    }

    public String getDeskripsiDonasi() {
        return deskripsiDonasi;
    }

    public void setDeskripsiDonasi(String deskripsiDonasi) {
        this.deskripsiDonasi = deskripsiDonasi;
    }

    public String getJumlahDonasi() {
        return jumlahDonasi;
    }

    public void setJumlahDonasi(String jumlahDonasi) {
        this.jumlahDonasi = jumlahDonasi;
    }

}
