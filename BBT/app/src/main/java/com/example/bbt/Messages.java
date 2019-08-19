package com.example.bbt;

public class Messages {

    private String NameID, msg, name;

    public Messages(){

    }

    public Messages(String msg, String NameID, String name) {
        this.NameID = NameID;
        this.msg = msg;
        this.name = name;
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
}
