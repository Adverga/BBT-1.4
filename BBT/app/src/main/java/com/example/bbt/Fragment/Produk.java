package com.example.bbt.Fragment;

public class Produk {
    private String judul;
    private String listAlat;
    private String listBahan;
    private String listLangkah;
    private String listInfo;
    private String image;

    public Produk() {
    }

    public Produk(String judul, String listAlat, String listBahan, String listLangkah, String listInfo, String image) {
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

    public String getListAlat() {
        return listAlat;
    }

    public void setListAlat(String listAlat) {
        this.listAlat = listAlat;
    }

    public String getListBahan() {
        return listBahan;
    }

    public void setListBahan(String listBahan) {
        this.listBahan = listBahan;
    }

    public String getListLangkah() {
        return listLangkah;
    }

    public void setListLangkah(String listLangkah) {
        this.listLangkah = listLangkah;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getListInfo() {
        return listInfo;
    }

    public void setListInfo(String listInfo) {
        this.listInfo = listInfo;
    }
}
