package com.example.login;

public class UserAccount {
    private String emailId;
    private String password;
    private String username;


    public UserAccount() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAccount(String emailId, String password, String username){
        emailId = this.emailId;
        password = this.password;
        username = this.username;
    }
}
