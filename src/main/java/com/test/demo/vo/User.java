package com.test.demo.vo;

import lombok.*;

import java.time.LocalDate;


public class User {
    private int id;
    private String userId;
    private String userPw;
    private String userNkname;
    private LocalDate userRegdate;

//    public User(String userId, String userPw, String userNkname, LocalDate userRegdate) {
//        this.userId = userId;
//        this.userPw = userPw;
//        this.userNkname = userNkname;
//        this.userRegdate = userRegdate;
//    }
//
//    public User(String userId) {
//        this.userId = userId;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserNkname() {
        return userNkname;
    }

    public void setUserNkname(String userNkname) {
        this.userNkname = userNkname;
    }

    public LocalDate getUserRegdate() {
        return userRegdate;
    }

    public void setUserRegdate(LocalDate userRegdate) {
        this.userRegdate = userRegdate;
    }
}
