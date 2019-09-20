package com.example.bbt;

public class Messages {

    private String NameID;
    private String msg;
    private String name;
    private String mod;
    private String time;
    private String date;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Messages(){

    }

    public Messages(String msg, String NameID, String name, String mod, String time, String date) {
        this.NameID = NameID;
        this.msg = msg;
        this.name = name;
        this.mod = mod;
        this.time = time;
        this.date = date;
    }

    public String getId() {
        return NameID;
    }

    public void setId(String NameID) {
        this.NameID = NameID;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getNama() {
        return name;
    }

    public void setNama(String nama) {
        this.name = nama;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }
}
