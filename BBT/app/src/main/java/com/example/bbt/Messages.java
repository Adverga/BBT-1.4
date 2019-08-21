package com.example.bbt;

public class Messages {

    private String NameID, msg, name, mod;

    public Messages(){

    }

    public Messages(String msg, String NameID, String name, String mod) {
        this.NameID = NameID;
        this.msg = msg;
        this.name = name;
        this.mod = mod;
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
