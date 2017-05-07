package com.example.hong_inseon.projectlouvre.dao;

import java.io.Serializable;

public class Workbook implements Serializable {

	private static final long serialVersionUID = -1615165095659294254L;

	//`wb_story`, `wb_exhi_no`, `wb_ms_no`
	private String wb_story;
	private int wb_exhi_no;
	private int wb_ms_no;
	private int wb_no;

	@Override
	public String toString() {
		return "Workbook{" +
				"wb_no=" + wb_no +
				", wb_story='" + wb_story + '\'' +
				", wb_exhi_no=" + wb_exhi_no +
				", wb_ms_no=" + wb_ms_no +
				'}';
	}

	public int getWb_no() {
		return wb_no;
	}

	public void setWb_no(int wb_no) {
		this.wb_no = wb_no;
	}

	public int getWb_ms_no() {
		return wb_ms_no;
	}

	public void setWb_ms_no(int wb_ms_no) {
		this.wb_ms_no = wb_ms_no;
	}

	public String getWb_story() {
		return wb_story;
	}

	public void setWb_story(String wb_story) {
		this.wb_story = wb_story;
	}

	public int getWb_exhi_no() {
		return wb_exhi_no;
	}

	public void setWb_exhi_no(int wb_exhi_no) {
		this.wb_exhi_no = wb_exhi_no;
	}

}
