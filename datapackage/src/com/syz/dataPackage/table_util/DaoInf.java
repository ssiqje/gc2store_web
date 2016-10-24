package com.syz.dataPackage.table_util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.syz.dataPackage.bean.User;
import com.syz.dataPackage.session_util.SessionUtil;
import com.syz.dataPackage.table.GcParameter;
import com.syz.dataPackage.table.In_store;
import com.syz.dataPackage.table.Out_store;
import com.syz.dataPackage.table.Store;
import com.syz.dataPackage.table.Summary;

public class DaoInf {
	private SessionUtil sessionUtil;
	private Session session;
	private Connection connection;
	private Statement statement;
	private String type;
	private In_store in_store;
	private Out_store out_store;
	private Store store;
	private GcParameter gcparameter;
	private Summary summary;

	public DaoInf() {
		sessionUtil = new SessionUtil();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * 将传送上来的数据执行更新到库
	 * 
	 * @return 成功 true 失败false
	 */
	public boolean runForTable(JSONObject up_data_json) {
		 JSONArray add_list = null;
		 JSONArray del_list = null;
		 String user_flag = null;
		if (up_data_json.has("user_flag")) {
			user_flag = up_data_json.getString("user_flag");
		}
		if (up_data_json.has("add_list")) {
			add_list = up_data_json.getJSONArray("add_list");
		}
		if (up_data_json.has("del_list")) {
			del_list = up_data_json.getJSONArray("del_list");
		}
		connection = sessionUtil.getConnection();
		if (save(add_list,user_flag) && del(del_list,user_flag)) {
			sessionUtil.closeConnection();
			return true;
		} else {
			sessionUtil.closeConnection();
			return false;
		}
	}

	/**
	 * 保存到数据库
	 * @param user_flag 
	 * 
	 * @return 成功 true 失败false
	 */
	public boolean save(JSONArray add_list, String user_flag) {
		// TODO Auto-generated method stub
		if (add_list == null || add_list.length() == 0) {
			return true;
		}
		System.out.println(add_list.length() + "个对象等待加入！");
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			for (Object object : add_list) {
				JSONObject jsonObject = (JSONObject) object;
				type = jsonObject.getString("type");
				Object object2 = getObjectForType(jsonObject);
				String querysql = getByIdSql(object2,user_flag);
				ResultSet resultset = statement.executeQuery(querysql);
				if (resultset.next()) {
					System.out.println("记录存在!");
					String upsql = getUpDataSql(object2,user_flag);
					System.out.println("准备执行的语句为：" + upsql);
					statement.execute(upsql);
				} else {
					System.out.println("记录不存在!");
					String savesql = getSqlSave(object2,user_flag);
					System.out.println("准备执行的语句为：" + savesql);
					statement.execute(savesql);
				}
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 获取一条插入语句
	 * 
	 * @param object
	 *            要插入的对象
	 * @param user_flag 
	 * @return 返回一条插入语句 ，失败返回NULL
	 */
	public String getSqlSave(Object object, String user_flag) {
		try {
			String sql = "insert into ";
			System.out.println("现在语句：" + sql);
			Class c = object.getClass();
			String table = c.getSimpleName() + user_flag;
			sql += table;
			System.out.println("现在语句：" + sql);
			Method[] ms = c.getDeclaredMethods();
			String keyString = " (";
			String valuesString = ")values(";
			for (Method method : ms) {
				String string = method.getName();

				if (string.startsWith("get")) {
					System.out.println(string + "~~~~~~~~~~~~~");
					try {
						String key = string.substring(3).toLowerCase();
						System.out.println("获取的" + key);
						keyString += key + ",";
						System.out.println("合并后的" + keyString);
						Object object2 = method.invoke(object);
						if (object2 instanceof String) {
							valuesString += "'" + object2 + "',";
						} else {
							valuesString += object2 + ",";
						}
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			keyString = keyString.substring(0, keyString.length() - 1);
			valuesString = valuesString.substring(0, valuesString.length() - 1);
			sql += keyString + valuesString + ")";
			System.out.println(sql);
			return sql;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除数据
	 * @param user_flag 
	 * @param user_flag 
	 * 
	 * @return 成功 true 失败false
	 */
	public boolean del(JSONArray del_list, String user_flag) {
		if (del_list == null || del_list.length() == 0) {
			return true;
		}
		System.out.println(del_list.length() + "个对象等待删除！");
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			for (Object object : del_list) {
				JSONObject jsonObject = (JSONObject) object;
				type = jsonObject.getString("type");
				Object object2 = getObjectForType(jsonObject);

				String sql = getDelSql(object2,user_flag);
				System.out.println("准备执行的语句为：" + sql);
				statement.execute(sql);
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return false;

	}

	/**
	 * 获取一个根据ID删除的SQl需要的参数
	 * 
	 * @param object
	 *            要删除的对象
	 * @param user_flag 
	 * @return 返回一个HashMap 含有mName,result,table
	 */
	private String getDelSql(Object object, String user_flag) {
		// TODO Auto-generated method stub
		Class c = object.getClass();
		String mName;

		String table = c.getSimpleName();
		String column = null;
		String result = null;
		Method[] ms = c.getDeclaredMethods();
		if (table.equals("In_store") || table.equals("Out_store")) {
			for (Method method : ms) {
				mName = method.getName().toLowerCase();
				if (mName.equals("getid")) {
					try {
						column = mName.substring(3, mName.length());
						result = method.invoke(object) + "";
						break;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		} else {
			for (Method method : ms) {
				mName = method.getName().toLowerCase();
				if (mName.equals("getkind_id")) {
					try {
						column = mName.substring(3, mName.length());
						result = "'" + method.invoke(object) + "'";
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}
		return "delete from " + table + user_flag + " where " + column + "=" + result;
	}

	/**
	 * 解析Json获得对象
	 * 
	 * @param jsonObject
	 *            对象Json
	 * @return 成功object 失败null
	 */
	private Object getObjectForType(JSONObject jsonObject) {
		if ("in_store".equals(type)) {
			in_store = new In_store();
			if (jsonObject.has("id"))
				in_store.setId(Long.parseLong(jsonObject.getString("id")));
			if (jsonObject.has("kind_id"))
				in_store.setKind_id(jsonObject.getString("kind_id"));
			if (jsonObject.has("weight_m"))
				in_store.setWeight_m(Float.parseFloat(jsonObject.getString("weight_m")));
			if (jsonObject.has("gc_long"))
				in_store.setGc_long(Float.parseFloat(jsonObject.getString("gc_long")));
			if (jsonObject.has("inpay_m"))
				in_store.setInpay_m(Float.parseFloat(jsonObject.getString("inpay_m")));
			if (jsonObject.has("count"))
				in_store.setCount(Integer.parseInt(jsonObject.getString("count")));
			if (jsonObject.has("wight"))
				in_store.setWight(Float.parseFloat(jsonObject.getString("wight")));
			if (jsonObject.has("allpay"))
				in_store.setAllpay(Float.parseFloat(jsonObject.getString("allpay")));
			if (jsonObject.has("date"))
				in_store.setDate(jsonObject.getString("date"));
			return in_store;
		} else if ("out_store".equals(type)) {
			out_store = new Out_store();
			if (jsonObject.has("id"))
				out_store.setId(Long.parseLong(jsonObject.getString("id")));
			if (jsonObject.has("kind_id"))
				out_store.setKind_id(jsonObject.getString("kind_id"));
			if (jsonObject.has("weight_m"))
				out_store.setWeight_m(Float.parseFloat(jsonObject.getString("weight_m")));
			if (jsonObject.has("gc_long"))
				out_store.setGc_long(Float.parseFloat(jsonObject.getString("gc_long")));
			if (jsonObject.has("outpay_m"))
				out_store.setOutpay_m(Float.parseFloat(jsonObject.getString("outpay_m")));
			if (jsonObject.has("count"))
				out_store.setCount(Integer.parseInt(jsonObject.getString("count")));
			if (jsonObject.has("wight"))
				out_store.setWight(Float.parseFloat(jsonObject.getString("wight")));
			if (jsonObject.has("allpay"))
				out_store.setAllpay(Float.parseFloat(jsonObject.getString("allpay")));
			if (jsonObject.has("date"))
				out_store.setDate(jsonObject.getString("date"));
			return out_store;
		} else if ("store".equals(type)) {
			store = new Store();
			if (jsonObject.has("kind_id"))
				store.setKind_id(jsonObject.getString("kind_id"));
			if (jsonObject.has("weight_m"))
				store.setWeight_m(Float.parseFloat(jsonObject.getString("weight_m")));
			if (jsonObject.has("gc_long"))
				store.setGc_long(Float.parseFloat(jsonObject.getString("gc_long")));
			if (jsonObject.has("count"))
				store.setCount(Integer.parseInt(jsonObject.getString("count")));
			if (jsonObject.has("wight"))
				store.setWight(Float.parseFloat(jsonObject.getString("wight")));
			return store;
		} else if ("summary".equals(type)) {
			summary = new Summary();
			if (jsonObject.has("id"))
				summary.setId(Integer.parseInt(jsonObject.getString("id")));
			if (jsonObject.has("weight_all"))
				summary.setWeight_all(Float.parseFloat(jsonObject.getString("weight_all")));
			if (jsonObject.has("inpay_all"))
				summary.setInpay_all(Float.parseFloat(jsonObject.getString("inpay_all")));
			if (jsonObject.has("outpay_all"))
				summary.setOutpay_all(Float.parseFloat(jsonObject.getString("outpay_all")));
			if (jsonObject.has("in_or_out_pay"))
				summary.setIn_or_out_pay(Float.parseFloat(jsonObject.getString("in_or_out_pay")));
			return summary;
		} else if ("gcparameter".equals(type)) {
			gcparameter = new GcParameter();
			if (jsonObject.has("kind_id"))
				gcparameter.setKind_id(jsonObject.getString("kind_id"));
			if (jsonObject.has("gc_long"))
				gcparameter.setGc_long(Float.parseFloat(jsonObject.getString("gc_long")));
			if (jsonObject.has("weight_m"))
				gcparameter.setWeight_m(Float.parseFloat(jsonObject.getString("weight_m")));
			return gcparameter;
		} else {
			return null;
		}
	}

	/**
	 * 获取一条更新语名
	 * 
	 * @param object
	 *            更新的对象
	 * @param user_flag 
	 * @return 返回更新语句，失败返回NULL
	 */
	public String getUpDataSql(Object object, String user_flag) {
		String sql = "update ";
		Class c = object.getClass();
		String table = c.getSimpleName() + user_flag;
		sql += table + " set ";
		String idString = null;
		Object valuesObject = null;
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			String mString = method.getName().toLowerCase();
			if (mString.startsWith("get")) {
				if (mString.endsWith("id")) {
					idString = mString.substring(3, mString.length());
					try {
						valuesObject = method.invoke(object);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					String key = mString.substring(3, mString.length());
					try {
						Object object2 = method.invoke(object);
						sql += key + "=";
						if (object2 instanceof String) {
							sql += "'" + object2 + "',";
						} else {
							sql += object2 + ",";
						}
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		sql = sql.substring(0, sql.length() - 1);
		if (valuesObject instanceof String) {
			sql += " where " + idString + "=" + "'" + valuesObject + "'";
		} else {
			sql += " where " + idString + "=" + valuesObject;
		}

		System.out.println(sql);
		return sql;
	}

	/**
	 * 获取此类型的所有记录
	 * @param className 要获取的类类型   db_id 表后缀
	 * @return 返回包含结果的Jsonarray
	 */
	public JSONArray getList(String db_id,Class... classtype) {
		// TODO Auto-generated method stub
		JSONArray result = new JSONArray();
		JSONObject jsonObject = null;
		String table=null;
		String sql = null;
		ResultSet rs=null;
		connection = sessionUtil.getConnection();
		try {
			statement = connection.createStatement();
			for (Class class1 : classtype) 
			{
				table = class1.getSimpleName()+db_id;
				sql = "select * from " + table;
				System.out.println(sql);
				if("GcParameter".equals(class1.getSimpleName()))
				{
					rs = statement.executeQuery(sql);
					if(rs!=null)
					{
						while(rs.next())
						{
							jsonObject = new JSONObject();
							jsonObject.put("type", "gcparameter");
							jsonObject.put("kind_id",rs.getString("kind_id"));
							jsonObject.put("weight_m",rs.getFloat("weight_m")+"");
							jsonObject.put("gc_long",rs.getFloat("gc_long")+"");

							result.put(jsonObject);
						}
						rs.close();
					}
					
					continue;
				}
				else if ("Out_store".equals(class1.getSimpleName())) 
				{
					
					 rs = statement.executeQuery(sql);
					 if(rs!=null)
						{
					 while (rs.next()) {
						 jsonObject = new JSONObject();
							jsonObject.put("type", "out_store");
							jsonObject.put("id", rs.getLong("id")+"");
							jsonObject.put("kind_id",rs.getString("kind_id"));
							jsonObject.put("weight_m",rs.getFloat("weight_m")+"");
							jsonObject.put("gc_long",rs.getFloat("gc_long")+"");
							jsonObject.put("outpay_m",rs.getFloat("outpay_m")+"");
							jsonObject.put("count",rs.getInt("count")+"");
							jsonObject.put("wight",rs.getFloat("wight")+"");
							jsonObject.put("allpay",rs.getFloat("allpay")+"");
							jsonObject.put("date",rs.getString("date"));
							result.put(jsonObject);
					 }
					 rs.close();}
					 continue;
			}else if ("In_store".equals(class1.getSimpleName())) 
				{
					
					 rs = statement.executeQuery(sql);
					 if(rs!=null)
						{
					 while (rs.next()) {
						 jsonObject = new JSONObject();
							jsonObject.put("type", "in_store");
							jsonObject.put("id", rs.getLong("id")+"");
							jsonObject.put("kind_id",rs.getString("kind_id"));
							jsonObject.put("weight_m",rs.getFloat("weight_m")+"");
							jsonObject.put("gc_long",rs.getFloat("gc_long")+"");
							jsonObject.put("inpay_m",rs.getFloat("inpay_m")+"");
							jsonObject.put("count",rs.getInt("count")+"");
							jsonObject.put("wight",rs.getFloat("wight")+"");
							jsonObject.put("allpay",rs.getFloat("allpay")+"");
							jsonObject.put("date",rs.getString("date"));

							result.put(jsonObject);
					 }
					 rs.close();}
					 continue;
			}else if ("Store".equals(class1.getSimpleName())) 
				{
				 rs = statement.executeQuery(sql);
				 if(rs!=null)
					{
				 while (rs.next()) {
					 jsonObject = new JSONObject();
						jsonObject.put("type", "store");
						jsonObject.put("kind_id",rs.getString("kind_id"));
						jsonObject.put("weight_m",rs.getFloat("weight_m")+"");
						jsonObject.put("gc_long",rs.getFloat("gc_long")+"");
						jsonObject.put("count",rs.getInt("count")+"");
						jsonObject.put("wight",rs.getFloat("wight")+"");
						result.put(jsonObject);
				 }
				 rs.close();}
				 continue;
				 }
				else if ("Summary".equals(class1.getSimpleName())) 
				 {
					 rs = statement.executeQuery(sql);
					 if(rs!=null)
						{
					 while (rs.next()) {
						 jsonObject = new JSONObject();
							jsonObject.put("type", "summary");
							jsonObject.put("id",rs.getInt("id")+"");
							jsonObject.put("in_or_out_pay",rs.getFloat("in_or_out_pay")+"");
							jsonObject.put("outpay_all",rs.getFloat("outpay_all")+"");
							jsonObject.put("weight_all",rs.getFloat("weight_all")+"");
							jsonObject.put("inpay_all",rs.getFloat("inpay_all")+"");
							result.put(jsonObject);
						 }
						 rs.close();}
						 continue;
						 }
			}
			sessionUtil.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sessionUtil.closeConnection();
			return null;
		}
		return result;
		
		
		
		


	}

	/**
	 * 通过对象获取一条通过ID查询的Sql
	 * 
	 * @param object
	 *            要查询的对象
	 * @param user_flag 
	 * @return sql
	 */
	public String getByIdSql(Object object, String user_flag) {
		// TODO Auto-generated method stub
		String sql = "select * from  ";
		Class c = object.getClass();
		String table = c.getSimpleName() + user_flag;
		sql += table + " where ";
		String idString = null;
		Object valuesObject = null;
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			String mString = method.getName().toLowerCase();
			if (mString.startsWith("get")) {
				if (mString.endsWith("id")) {
					idString = mString.substring(3, mString.length());
					try {
						valuesObject = method.invoke(object);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

			}
		}

		if ("String".equals(valuesObject.getClass().getSimpleName())) {
			sql += idString + "='" + valuesObject + "'";
		} else {
			sql += idString + "=" + valuesObject;
		}
		System.out.println(sql);
		return sql;
	}

	/**
	 * 启用事务更新出库表、库存表、总表
	 * 
	 * @param out_store
	 *            出库表
	 * @param store
	 *            库存表
	 * @param summary
	 *            总表
	 * @return 成功 true，失败 fale
	 */
//	public boolean saveOutItemWater(Out_store out_store, Store store, Summary summary) {
//		 // TODO Auto-generated method stub
//		 String out_store_save_sql=getSqlSave(out_store);
//		 String store_updata_sql=getUpDataSql(store);
//		 String summary_updata_sql=getUpDataSql(summary);
//		 db=dbHelper.getWritableDatabase();
//		 db.execSQL(out_store_save_sql);
//		 db.execSQL(store_updata_sql);
//		 db.execSQL(summary_updata_sql);
//		 close();
//		return true;
//	}

	/**
	 * 启用事务更新进库表、库存表、总表
	 * 
	 * @param in_store
	 *            出库表
	 * @param store
	 *            库存表
	 * @param summary
	 *            总表
	 * @return 成功 true，失败 fale
	 */
//	public boolean delInStoreWater(In_store in_store, Store store, Summary summary) {
//		 TODO Auto-generated method stub
//		 HashMap<String, String> list = getDelParameter(in_store);
//		String store_updata_sql = getUpDataSql(store);
//		String summary_updata_sql = getUpDataSql(summary);
//		 db=dbHelper.getWritableDatabase();
//		 db.beginTransaction();
//		 try {
//		 db.delete(list.get("table"), list.get("column").substring(3,
//		 list.get("column").length()), new String[]{list.get("result")});
//		 db.execSQL(store_updata_sql);
//		 db.execSQL(summary_updata_sql);
//		 //db.setTransactionSuccessful();
//		 close();
//		return true;
//		 } catch (SQLException e) {
//		 TODO Auto-generated catch block
//		 e.printStackTrace();
//		 db.endTransaction();
//		 close();
//		 }
//		 return false;
//	}
	 /**
	 * 更新数据
	 *
	 * @return 成功 true 失败false
	 */
//	 public boolean upData() {
//	 if(up_list==null||up_list.length()==0)
//	 {
//	 System.out.println("~~~~~~~~~~~~~个对象等待更新！");
//	 return true;
//	 }
//	 System.out.println(up_list.length()+"个对象等待更新！");
//	 try {
//	 connection.setAutoCommit(false);
//	 statement = connection.createStatement();
//	 for (Object object : up_list) {
//	 JSONObject jsonObject = (JSONObject)object;
//	 type=jsonObject.getString("type");
//	 Object object2 = getObjectForType(jsonObject);
//	 System.out.println("本次要更新的类型是："+object2.getClass().getName());
//	 String sql = getUpDataSql(object2);
//	 System.out.println("准备执行的语句为："+sql);
//	 statement.execute(sql);
//	 }
//	 connection.commit();
//	 return true;
//	 } catch (SQLException e) {
//	 // TODO Auto-generated catch block
//	 e.printStackTrace();
//	 try {
//	 connection.rollback();
//	 } catch (SQLException e1) {
//	 // TODO Auto-generated catch block
//	 e1.printStackTrace();
//	 }
//	 }
//	
//	 return false;
//	 }

	public void unline(User user) {
		session = sessionUtil.getSession();
		Transaction t = session.beginTransaction();
		session.update(user);
		t.commit();
		session.close();
		sessionUtil.close();
		
	}

//	 public Object getById(Class className, String id) {
//	 // TODO Auto-generated method stub
//	 db = dbHelper.getWritableDatabase();
//	 String table = className.getSimpleName();
//	 String sql = "select * from " + table + " where kind_id='" + id +
//	 "'";
//	 System.out.println(sql);
//	 try {
//	 cursor = db.rawQuery(sql, null);
//	 if (cursor.moveToNext()) {
//	 if ("GcParameter".equals(table)) {
//	 GcParameter gcParameter = new GcParameter();
//	 gcParameter.setKind_id(cursor.getString(cursor
//	 .getColumnIndex("kind_id")));
//	 gcParameter.setWeight_m(cursor.getFloat(cursor
//	 .getColumnIndex("weight_m")));
//	 gcParameter.setGc_long(cursor.getFloat(cursor
//	 .getColumnIndex("gc_long")));
//	 close();
//	 return gcParameter;
//	 } else if ("In_store".equals(table)) {
//	 In_store in_store = new In_store();
//	 in_store.setKind_id(cursor.getString(cursor
//	 .getColumnIndex("kind_id")));
//	 in_store.setWeight_m(cursor.getFloat(cursor
//	 .getColumnIndex("weight_m")));
//	 in_store.setGc_long(cursor.getFloat(cursor
//	 .getColumnIndex("gc_long")));
//	 in_store.setInpay_m(cursor.getFloat(cursor
//	 .getColumnIndex("inpay_m")));
//	 in_store.setCount(cursor.getInt(cursor
//	 .getColumnIndex("count")));
//	 in_store.setWight(cursor.getFloat(cursor
//	 .getColumnIndex("wight")));
//	 in_store.setAllpay(cursor.getFloat(cursor
//	 .getColumnIndex("allpay")));
//	 in_store.setDate(cursor.getString(cursor
//	 .getColumnIndex("date")));
//	 close();
//	 return in_store;
//	
//	 } else if ("Out_store".equals(table)) {
//	 Out_store out_store = new Out_store();
//	 out_store.setKind_id(cursor.getString(cursor
//	 .getColumnIndex("kind_id")));
//	 out_store.setWeight_m(cursor.getFloat(cursor
//	 .getColumnIndex("weight_m")));
//	 out_store.setGc_long(cursor.getFloat(cursor
//	 .getColumnIndex("gc_long")));
//	 out_store.setOutpay_m(cursor.getFloat(cursor
//	 .getColumnIndex("inpay_m")));
//	 out_store.setCount(cursor.getInt(cursor
//	 .getColumnIndex("count")));
//	 out_store.setWight(cursor.getFloat(cursor
//	 .getColumnIndex("wight")));
//	 out_store.setAllpay(cursor.getFloat(cursor
//	 .getColumnIndex("allpay")));
//	 out_store.setDate(cursor.getString(cursor
//	 .getColumnIndex("date")));
//	 close();
//	 return out_store;
//	
//	 } else if ("Store".equals(table)) {
//	 Store store = new Store();
//	 store.setKind_id(cursor.getString(cursor
//	 .getColumnIndex("kind_id")));
//	 store.setWeight_m(cursor.getFloat(cursor
//	 .getColumnIndex("weight_m")));
//	 store.setGc_long(cursor.getFloat(cursor
//	 .getColumnIndex("gc_long")));
//	 store.setCount(cursor.getInt(cursor.getColumnIndex("count")));
//	 store.setWight(cursor.getFloat(cursor
//	 .getColumnIndex("wight")));
//	 close();
//	 return store;
//	
//	 }
//	 return null;
//	 }
//	 } catch (SQLException e) {
//	 // TODO Auto-generated catch block
//	 e.printStackTrace();
//	 close();
//	 }
//	 return null;
//	 }

}
