package com.example.gambungstore.models;

public class LaporanDonasi {
    String judulLaporan;
    int userImage;
    String usernamePenerima;
    String deskripsiDonasi;
    int kas_donasi;
    int penarikan_donasi;
    int total_kas;

    public LaporanDonasi(String judulLaporan, int userImage, String usernamePenerima,
                         String deskripsiDonasi, int kas_donasi, int penarikan_donasi, int total_kas) {
        this.judulLaporan = judulLaporan;
        this.userImage = userImage;
        this.usernamePenerima = usernamePenerima;
        this.deskripsiDonasi = deskripsiDonasi;
        this.kas_donasi = kas_donasi;
        this.penarikan_donasi = penarikan_donasi;
        this.total_kas = total_kas;
    }

    public String getJudulLaporan() {
        return judulLaporan;
    }

    public void setJudulLaporan(String judulLaporan) {
        this.judulLaporan = judulLaporan;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUsernamePenerima() {
        return usernamePenerima;
    }

    public void setUsernamePenerima(String usernamePenerima) {
        this.usernamePenerima = usernamePenerima;
    }

    public String getDeskripsiDonasi() {
        return deskripsiDonasi;
    }

    public void setDeskripsiDonasi(String deskripsiDonasi) {
        this.deskripsiDonasi = deskripsiDonasi;
    }

    public int getKas_donasi() {
        return kas_donasi;
    }

    public void setKas_donasi(int kas_donasi) {
        this.kas_donasi = kas_donasi;
    }

    public int getPenarikan_donasi() {
        return penarikan_donasi;
    }

    public void setPenarikan_donasi(int penarikan_donasi) {
        this.penarikan_donasi = penarikan_donasi;
    }

    public int getTotal_kas() {
        return total_kas;
    }

    public void setTotal_kas(int total_kas) {
        this.total_kas = total_kas;
    }
}
