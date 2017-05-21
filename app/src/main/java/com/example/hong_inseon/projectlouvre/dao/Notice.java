package com.example.hong_inseon.projectlouvre.dao;

import java.util.Date;

public class Notice {
    private int note_no;
    private String ms_name;
    private String note_title;
    private String note_content;
    private Date note_date;

    public Notice() {}

    public Notice(int note_no, String ms_name, String note_title, String note_content, Date note_date) {
        this.note_no = note_no;
        this.ms_name = ms_name;
        this.note_title = note_title;
        this.note_content = note_content;
        this.note_date = note_date;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "note_no='" + note_no + '\'' +
                ", ex_no='" + ms_name + '\'' +
                ", note_title='" + note_title + '\'' +
                ", note_content='" + note_content + '\'' +
                ", note_date='" + note_date + '\'' +
                '}';
    }

    public int getNote_no() {
        return note_no;
    }

    public void setNote_no(int note_no) {
        this.note_no = note_no;
    }

    public String getMs_name() {
        return ms_name;
    }

    public void setMs_name(String ms_name) {
        this.ms_name = ms_name;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public Date getNote_date() {
        return note_date;
    }

    public void setNote_date(Date note_date) {
        this.note_date = note_date;
    }
}
