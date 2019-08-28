package com.example.bbt.Fragment;

import java.util.List;

public class Produk {
    private String judul;
    private List<String> listAlat;
    private List<String> listBahan;
    private List<String> listLangkah;
    private List<String> listInfo;
    private int image;

    public Produk() {
    }

    public Produk(String judul, List<String> listAlat, List<String> listBahan, List<String> listLangkah, List<String> listInfo, int image) {
        this.judul = judul;
        this.listAlat = listAlat;
        this.listBahan = listBahan;
        this.listLangkah = listLangkah;
        this.listInfo = listInfo;
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public List<String> getListAlat() {
        return listAlat;
    }

    public void setListAlat(List<String> listAlat) {
        this.listAlat = listAlat;
    }

    public List<String> getListBahan() {
        return listBahan;
    }

    public void setListBahan(List<String> listBahan) {
        this.listBahan = listBahan;
    }

    public List<String> getListLangkah() {
        return listLangkah;
    }

    public void setListLangkah(List<String> listLangkah) {
        this.listLangkah = listLangkah;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<String> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<String> listInfo) {
        this.listInfo = listInfo;
    }
}
