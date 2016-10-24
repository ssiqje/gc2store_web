package com.syz.dataPackage.table;

import java.util.ArrayList;

public class Summary
{
	private int id;
	private float weight_all;
	private float inpay_all;
	private float outpay_all;
	private float in_or_out_pay;
	
	public Summary(){}
	public Summary(int id, float weight_all, float inpay_all, float outpay_all, float in_or_out_pay) {
		super();
		this.id = id;
		this.weight_all = weight_all;
		this.inpay_all = inpay_all;
		this.outpay_all = outpay_all;
		this.in_or_out_pay = in_or_out_pay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getWeight_all() {
		return weight_all;
	}
	public void setWeight_all(float weight_all) {
		this.weight_all = weight_all;
	}
	public float getInpay_all() {
		return inpay_all;
	}
	public void setInpay_all(float inpay_all) {
		this.inpay_all = inpay_all;
	}
	public float getOutpay_all() {
		return outpay_all;
	}
	public void setOutpay_all(float outpay_all) {
		this.outpay_all = outpay_all;
	}
	public float getIn_or_out_pay() {
		return in_or_out_pay;
	}
	public void setIn_or_out_pay(float in_or_out_pay) {
		this.in_or_out_pay = in_or_out_pay;
	}
	
	
	@Override
	public String toString() {
		return "Summary [id=" + id + ", weight_all=" + weight_all
				+ ", inpay_all=" + inpay_all + ", outpay_all=" + outpay_all
				+ ", in_or_out_pay=" + in_or_out_pay + "]";
	}
	public String[][] excelGetMessage()
	{
		return new String[][]{
								{"总表"},
								{"编号","现在吨数","总进价","总售价","盈亏"},
								{"id","weight_all","inpay_all","outpay_all","in_or_out_pay"}
								
							  };
		
	}

}
