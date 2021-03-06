package com.syz.dataPackage.table;

import java.util.ArrayList;

public class In_store
{
	private long id;
	private String kind_id;
	private float weight_m;
	private float gc_long;
	private float inpay_m;
	private int count;
	private float wight;
	private float allpay;
	private String date;
	public In_store() {}
	public In_store(long id, String kind_id, float weight_m, float gc_long, float inpay_m, int count, float wight,
			float allpay, String date) {
		super();
		this.id = id;
		this.kind_id = kind_id;
		this.weight_m = weight_m;
		this.gc_long = gc_long;
		this.inpay_m = inpay_m;
		this.count = count;
		this.wight = wight;
		this.allpay = allpay;
		this.date = date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKind_id() {
		return kind_id;
	}
	public void setKind_id(String kind_id) {
		this.kind_id = kind_id;
	}
	public float getWeight_m() {
		return weight_m;
	}
	public void setWeight_m(float weight_m) {
		this.weight_m = weight_m;
	}
	public float getGc_long() {
		return gc_long;
	}
	public void setGc_long(float gc_long) {
		this.gc_long = gc_long;
	}
	public float getInpay_m() {
		return inpay_m;
	}
	public void setInpay_m(float inpay_m) {
		this.inpay_m = inpay_m;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getWight() {
		return wight;
	}
	public void setWight(float wight) {
		this.wight = wight;
	}
	public float getAllpay() {
		return allpay;
	}
	public void setAllpay(float allpay) {
		this.allpay = allpay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "In_store [id=" + id + ", kind_id=" + kind_id + ", weight_m="
				+ weight_m + ", gc_long=" + gc_long + ", inpay_m=" + inpay_m
				+ ", count=" + count + ", wight=" + wight + ", allpay="
				+ allpay + ", date=" + date + "]";
	}
	/**
	 * 获取中文表名，种文字段名，英文字段名
	 * @return 返回中文表名，种文字段名，英文字段名
	 */
	public String[][] excelGetMessage()
	{
		return new String[][]{
								{"入库表"},
								{"流水编号","种类编号","米/吨","根/M","进价","根数","总重(吨)","总付款","日期"},
								{"id","kind_id","weight_m","gc_long","inpay_m","count","wight","allpay","date"}
								
							  };
		
	}
	
	

	
}
