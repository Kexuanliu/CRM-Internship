package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

public class City {
	
	@Expose
	private int id;
	@Expose
	private String name;
	@Expose
	private String cityId;
	@Expose
	private String provinceId;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCName() {
		return this.name;
	}
	
	public void setCName(String name) {
		this.name = name;
	}
	
	public String getCId() {
		return this.cityId;
	}
	
	public void setCId(String cid) {
		this.cityId = cid;
	}
	
	public String getPId() {
		return this.provinceId;
	}
	
	public void setPId(String pid) {
		this.provinceId = pid;
	}
}

