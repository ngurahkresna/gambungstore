package com.example.gambungstore.models;

import android.media.Image;

public class BeritaDonasi {
    int foto;
    String judul;
    String isi;

    public BeritaDonasi(int foto, String judul, String isi)
    {
        this.foto = foto;
        this.judul = judul;
        this.isi = isi;
    }
    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
