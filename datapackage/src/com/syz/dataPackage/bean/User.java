package com.syz.dataPackage.bean;

public class User 
{
	private Integer id;
	private String photoImage;
	private String user_name;
	private String user_psw;
	private String gander;
	private String qq;
	private String hobbly;
	private String signature;
	private Integer line;
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoImage() {
		return photoImage;
	}
	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_psw() {
		return user_psw;
	}
	public void setUser_psw(String user_psw) {
		this.user_psw = user_psw;
	}
	public String getGander() {
		return gander;
	}
	public void setGander(String gander) {
		this.gander = gander;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getHobbly() {
		return hobbly;
	}
	public void setHobbly(String hobbly) {
		this.hobbly = hobbly;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", photoImage=" + photoImage + ", user_name=" + user_name + ", user_psw=" + user_psw
				+ ", gander=" + gander + ", qq=" + qq + ", hobbly=" + hobbly + ", signature=" + signature + ", line="
				+ line + "]";
	}
	
	
	
}
