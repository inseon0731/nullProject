package com.example.hong_inseon.projectlouvre.dao;

public class Piece {
    private String ms_no;
    private String ex_no;
    private String pc_no;
    private String pc_name;
    private String pc_author;
    private String pc_make;
    private String pc_exp;
    private String pc_img;
    private String pc_audio;
    private String pc_size;

    public Piece(String ms_no, String ex_no, String pc_no, String pc_name, String pc_author,String pc_make, String pc_exp,String pc_img,String pc_audio, String pc_size) {
        this.ms_no=ms_no;
        this.ex_no=ex_no;
        this.pc_no=pc_no;
        this.pc_name=pc_name;
        this.pc_author=pc_author;
        this.pc_make=pc_make;
        this.pc_exp=pc_exp;
        this.pc_img=pc_img;
        this.pc_audio=pc_audio;
        this.pc_size=pc_size;
    }

    public Piece() {}

    @Override
    public String toString() {
        return "Piece{" +
                "ms_no='" + ms_no + '\'' +
                ", ex_no='" + ex_no + '\'' +
                ", pc_no='" + pc_no + '\'' +
                ", pc_name='" + pc_name + '\'' +
                ", pc_author='" + pc_author + '\'' +
                ", pc_make='" + pc_make + '\'' +
                ", pc_exp='" + pc_exp + '\'' +
                ", pc_img='" + pc_img + '\'' +
                ", pc_audio='" + pc_audio + '\'' +
                ", pc_size='" + pc_size + '\'' +
                '}';
    }

    public String getMs_no() {
        return ms_no;
    }

    public void setMs_no(String ms_no) {
        this.ms_no = ms_no;
    }

    public String getEx_no() {
        return ex_no;
    }

    public void setEx_no(String ex_no) {
        this.ex_no = ex_no;
    }

    public String getPc_no() {
        return pc_no;
    }

    public void setPc_no(String pc_no) {
        this.pc_no = pc_no;
    }

    public String getPc_name() {
        return pc_name;
    }

    public void setPc_name(String pc_name) {
        this.pc_name = pc_name;
    }

    public String getPc_author() {
        return pc_author;
    }

    public void setPc_author(String pc_author) {
        this.pc_author = pc_author;
    }

    public String getPc_make() {
        return pc_make;
    }

    public void setPc_make(String pc_make) {
        this.pc_make = pc_make;
    }

    public String getPc_exp() {
        return pc_exp;
    }

    public void setPc_exp(String pc_exp) {
        this.pc_exp = pc_exp;
    }

    public String getPc_img() {
        return pc_img;
    }

    public void setPc_img(String pc_img) {
        this.pc_img = pc_img;
    }

    public String getPc_audio() {
        return pc_audio;
    }

    public void setPc_audio(String pc_audio) {
        this.pc_audio = pc_audio;
    }

    public String getPc_size() {
        return pc_size;
    }

    public void setPc_size(String pc_size) {
        this.pc_size = pc_size;
    }
}
