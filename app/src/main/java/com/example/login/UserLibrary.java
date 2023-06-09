package com.example.login;

public class UserLibrary {

    private String uid;

    public UserLibrary(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserLibrary(String uid){
        this.uid = uid;
    }

}
