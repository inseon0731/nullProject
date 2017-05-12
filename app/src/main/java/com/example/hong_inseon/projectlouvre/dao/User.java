package com.example.hong_inseon.projectlouvre.dao;

public class User {
    private String user_no;
    private String user_name;
    private String user_email;
    private String user_pw;
    private String user_gender;

    public User(String user_no,String user_name, String user_email, String user_pw, String user_gender) {
        this.user_no=user_no;
        this.user_name=user_name;
        this.user_email=user_email;
        this.user_pw=user_pw;
        this.user_gender=user_gender;
    }

    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "user_no='" + user_no + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_gender='" + user_gender + '\'' +
                '}';
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
}
