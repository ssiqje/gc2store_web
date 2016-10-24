package com.syz.dataPackage.table_util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.syz.dataPackage.session_util.SessionUtil;

public class TableUtil {
	private SessionUtil sessionUtil;
	private Connection connection;
	private static final String start = "CREATE TABLE ";
	// ������
	private static final String In_store_start = "In_store";
	private static final String In_store = " (" + "id  bigint PRIMARY KEY," + "kind_id  VARCHAR(20) ,"
			+ "weight_m DECIMAL NOT NULL," + "gc_long     DECIMAL NOT NULL," + "inpay_m  DECIMAL NOT NULL,"
			+ "count    INTEGER NOT NULL," + "wight    DECIMAL NOT NULL," + "allpay   DECIMAL NOT NULL,"
			+ "date     DATETIME NOT NULL," + "add_flag   INTEGER NOT NULL DEFAULT 0,"
			+ "modfiy_flag   INTEGER NOT NULL DEFAULT 0" + ")";
	// ������
	private static final String Out_store_start = "Out_store";
	private static final String Out_store = " (" + "id  bigint PRIMARY KEY," + "kind_id  VARCHAR(20) ,"
			+ "weight_m DECIMAL NOT NULL," + "gc_long     DECIMAL NOT NULL," + "outpay_m  DECIMAL NOT NULL,"
			+ "count    INTEGER NOT NULL," + "wight    DECIMAL NOT NULL," + "allpay   DECIMAL NOT NULL,"
			+ "date     DATETIME NOT NULL," + "add_flag   INTEGER NOT NULL DEFAULT 0,"
			+ "modfiy_flag   INTEGER NOT NULL DEFAULT 0" + ")";
	// �ⴢ��
	private static final String Store_start = "Store";
	private static final String Store = " (" + "kind_id  VARCHAR(20)  PRIMARY KEY," + "weight_m DECIMAL NOT NULL,"
			+ "gc_long     DECIMAL NOT NULL," + "count    INTEGER NOT NULL," + "wight    DECIMAL NOT NULL,"
			+ "add_flag   INTEGER NOT NULL DEFAULT 0," + "modfiy_flag   INTEGER NOT NULL DEFAULT 0" + ")";
	// �����
	private static final String GcParameter_start = "GcParameter";
	private static final String GcParameter = " (" + "kind_id  VARCHAR(20)  PRIMARY KEY," + "weight_m DECIMAL NOT NULL,"
			+ "gc_long     DECIMAL NOT NULL," + "add_flag   INTEGER NOT NULL DEFAULT 0,"
			+ "modfiy_flag   INTEGER NOT NULL DEFAULT 0" + ")";
	// �ܱ�
	private static final String Summary_start = "Summary";
	private static final String Summary = " (" + "id  integer PRIMARY KEY," + "weight_all DECIMAL NOT NULL,"
			+ "inpay_all  DECIMAL NOT NULL," + "outpay_all DECIMAL NOT NULL," + "in_or_out_pay  DECIMAL NOT NULL,"
			+ "add_flag   INTEGER NOT NULL DEFAULT 0," + "modfiy_flag   INTEGER NOT NULL DEFAULT 0" + ")";

	public TableUtil() {
		// TODO Auto-generated constructor stub
		sessionUtil = new SessionUtil();
	}

	/**
	 * Ϊ�û�����
	 * @param pTableNameEnd �û���ʶ
	 * @return �ɹ�true��ʧ��false
	 */
	public boolean createrTable(String pTableNameEnd) {
		String in_storeSql = start + In_store_start + pTableNameEnd + In_store;
		String out_storeSql = start + Out_store_start + pTableNameEnd + Out_store;
		String storeSql = start + Store_start + pTableNameEnd + Store;
		String parameterSql = start + GcParameter_start + pTableNameEnd + GcParameter;
		String summarySql = start + Summary_start + pTableNameEnd + Summary;
		System.out.println(in_storeSql);
		connection = sessionUtil.getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.execute(in_storeSql);
			statement.execute(out_storeSql);
			statement.execute(storeSql);
			statement.execute(parameterSql);
			statement.execute(summarySql);
			sessionUtil.closeConnection();
			System.out.println("����ɹ���");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("����ʧ�ܣ�");
			sessionUtil.closeConnection();
			return false;
		}

	}
	/**
	 * ɾ���û��ı�
	 * @param pTableNameFEnd �û���ʶ
	 * * @return �ɹ�true��ʧ��false
	 */
	public boolean delTable(String pTableNameFEnd) {
		sessionUtil.closeConnection();
		return false;

	}
	public boolean upDataToDB(String pTableNameFEnd)
	{
		sessionUtil.closeConnection();
		return false;
		
	}

}
