package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

public class Province {
	
	@Expose
	private int id;
	@Expose
	private String name;
	@Expose
	private String provinceId;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPName() {
		return this.name;
	}
	
	public void setPName(String name) {
		this.name = name;
	}
	
	public String getPId() {
		return this.provinceId;
	}
	
	public void setPId(String pid) {
		this.provinceId = pid;
	}
}

