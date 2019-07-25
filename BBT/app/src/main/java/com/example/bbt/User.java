package com.example.bbt;

public class User {

    private String id, username, email;
    private Boolean Moderator;

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getModerator(){ return Moderator; }

    public void setModerator(Boolean moderator) {
        Moderator = moderator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

