package com.shankha.letschat.Models;

public class Users {
    String profilPic, userName, email, password, userId, lastMessage, status;



    public Users(){}

    //signup constructor

    public Users( String userName, String email, String password) {

        this.userName = userName;
        this.email = email;
        this.password = password;



    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilPic() {
        return profilPic;
    }

    public void setProfilPic(String profilPic) {
        this.profilPic = profilPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Users(String profilPic, String userName, String email, String password, String userId, String lastMessage, String status) {
        this.profilPic = profilPic;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }
}
