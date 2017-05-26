package com.example.hong_inseon.projectlouvre.dao;

public class Review {
    private int review_no;
    private int ms_no;
    private int ex_no;
    private float review_rating;
    private String review_content;
    private String review_date;
    private String user_name;

    public Review() {}

    public Review(int review_no, int ms_no, int ex_no, String user_name, String review_content, float review_rating, String review_date) {
        this.ms_no = ms_no;
        this.ex_no = ex_no;
        this.user_name = user_name;
        this.review_no = review_no;
        this.review_content = review_content;
        this.review_rating = review_rating;
        this.review_date = review_date;
    }

    public int getReview_no() {
        return review_no;
    }

    public void setReview_no(int review_no) {
        this.review_no = review_no;
    }

    public int getMs_no() {
        return ms_no;
    }

    public void setMs_no(int ms_no) {
        this.ms_no = ms_no;
    }

    public int getEx_no() {
        return ex_no;
    }

    public void setEx_no(int ex_no) {
        this.ex_no = ex_no;
    }

    public float getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(float review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
