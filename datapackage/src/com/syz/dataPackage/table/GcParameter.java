package com.syz.dataPackage.table;

public class GcParameter
{
	private String kind_id;
	private float weight_m;
	private float gc_long;
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
	
	
	public GcParameter() {}
	public GcParameter(String kind_id, float weight_m, float gc_long) {
		this.kind_id = kind_id;
		this.weight_m = weight_m;
		this.gc_long = gc_long;
	}
	@Override
	public String toString() {
		return "GcParameter [kind_id=" + kind_id + ", weight_m=" + weight_m
				+ ", gc_long=" + gc_long + "]";
	}
	/**
	 * ��ȡ���ı����������ֶ�����Ӣ���ֶ���
	 * @return �������ı����������ֶ�����Ӣ���ֶ���
	 */
	public String[][] excelGetMessage()
	{
		return new String[][]{
								{"�����"},
								{"������","��/��","��/M"},
								{"kind_id","weight_m","gc_long"}
								
							  };
		
	}
	
}
