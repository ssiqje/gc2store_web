package com.syz.dataPackage.servel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.syz.dataPackage.bean.ResponseMessage;
import com.syz.dataPackage.bean.User;
import com.syz.dataPackage.session_util.SessionUtil;
import com.syz.dataPackage.table.GcParameter;
import com.syz.dataPackage.table.In_store;
import com.syz.dataPackage.table.Out_store;
import com.syz.dataPackage.table.Store;
import com.syz.dataPackage.table.Summary;
import com.syz.dataPackage.table_util.DaoInf;
import com.syz.dataPackage.table_util.TableUtil;

/**
 * Servlet implementation class Userdate
 */
public class Userdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String action;
	private SessionUtil sessionUtil;
	private Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Userdate() {
		super();
		sessionUtil = new SessionUtil();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub


		action = request.getParameter("action");
		//OutputStream out = response.getOutputStream();
		
		System.out.println(action);

		if ("user_regedit".equals(action)) 
		{
			String user_json_string = request.getParameter("userJson");
			System.out.println("���յ���Json:" + user_json_string);
			if (user_json_string != null) {

				JSONObject user_json = userRegedit(user_json_string);
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setAction("user_regedit");
				responseMessage.setResult(user_json != null ? "pass" : "fail");
				responseMessage.setJson_message(user_json.toString());
				JSONObject result = new JSONObject(responseMessage);
				System.out.println("ע������" + result.toString());
				PrintWriter writer = response.getWriter();
				writer.write(result.toString());

			}
 
		}

		if ("get_user_by_name".equals(action)) {
			String name = request.getParameter("user_name");
			System.out.println("Ҫ��ѯ���û����ǣ�" + name);
			ResponseMessage message = new ResponseMessage();
			if (name != null) {
				// String user_json = getUserByName(name);
				// System.out.println("��ѯ�����û�JSON:"+user_json);
				if (getUserByName(name) != null) {
					message.setAction("is_name_ok");
					message.setResult("no");
					JSONObject jsonmessage = new JSONObject(message);
					PrintWriter writer = response.getWriter();
					writer.write(jsonmessage.toString());
				} else {
					message.setAction("is_name_ok");
					message.setResult("yes");
					JSONObject jsonmessage = new JSONObject(message);
					PrintWriter writer = response.getWriter();
					writer.write(jsonmessage.toString());
				}
			}
		}
		if ("get_photo_add_list".equals(action)) {
			JSONObject json_photo_add_list = getPhotoAddList(request);
			System.out.println(json_photo_add_list.toString());
			PrintWriter writer = response.getWriter();
			writer.write(json_photo_add_list.toString());
		}
		
		if("login_user".equals(action))
		{
			String s = request.getParameter("user_json");
			if(s!=null&&!"".equals(s))
			{
				JSONObject jsonobject = new JSONObject(s);
				int id=jsonobject.getInt("id");
				String user_psw=jsonobject.getString("user_psw");
				User user=  findUserByNameAndPsw(id,user_psw);
				if(user!=null)
				{
					if(user.getLine()==1)
					{
						ResponseMessage message = new ResponseMessage();
						message.setAction("login_user");
						message.setResult("fail");
						message.setJson_message("�����������豸�ϵ����ˣ�");
						JSONObject result = new JSONObject(message);
						//out.write(result.toString().getBytes());
						PrintWriter writer = response.getWriter();
						writer.write(result.toString());
						System.out.println("���صĽ���ǣ�"+result.toString());
					}else
					{
						ResponseMessage message = new ResponseMessage();
						message.setAction("login_user");
						message.setResult("pass");
						message.setJson_message(new JSONObject(user).toString());
						JSONObject result = new JSONObject(message);
						//out.write(result.toString().getBytes());
						PrintWriter writer = response.getWriter();
						writer.write(result.toString());
						//�����û�����״̬
						user.setLine(1);
						//�������ݿ�
						session = sessionUtil.getSession();
						Transaction t = session.beginTransaction();
						session.update(user);
						t.commit();
						System.out.println("���ĺ���û���"+user.toString());
						System.out.println("���صĽ���ǣ�"+result.toString());
					}
				}else
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("login_user");
					message.setResult("fail");
					message.setJson_message("�û�ID���������");
					JSONObject result = new JSONObject(message);
					PrintWriter writer = response.getWriter();
					writer.write(result.toString());
					System.out.println("���صĽ���ǣ�"+result.toString());
				}
			}
		}
		if("up_user_data".equals(action))
		{
			String s = request.getParameter("user_json");
			if(s!=null&&!"".equals(s))
			{
				JSONObject jsonobject = new JSONObject(s);
				
				if(upUserData(jsonobject))
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("up_user_data");
					message.setResult("pass");
					JSONObject result = new JSONObject(message);
					//out.write(result.toString().getBytes());
					PrintWriter writer = response.getWriter();
					writer.write(result.toString());
					System.out.println("���صĽ���ǣ�"+result.toString());
				}else
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("login_user");
					message.setResult("fail");
					JSONObject result = new JSONObject(message);
					PrintWriter writer = response.getWriter();
					writer.write(result.toString());
					System.out.println("���صĽ���ǣ�"+result.toString());
				}
			}
		}
		if("un_line".equals(action))
		{
			String s = request.getParameter("user_json");
			if(s!=null&&!"".equals(s))
			{
				JSONObject jsonobject = new JSONObject(s);
				
				if(unLine(jsonobject))
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("un_line");
					message.setResult("pass");
					JSONObject result = new JSONObject(message);
					//out.write(result.toString().getBytes());
					PrintWriter writer = response.getWriter();
					writer.write(result.toString());
					System.out.println("���صĽ���ǣ�"+result.toString());
				}else
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("un_line");
					message.setResult("fail");
					JSONObject result = new JSONObject(message);
					PrintWriter writer = response.getWriter();
					writer.write(result.toString());
					System.out.println("���صĽ���ǣ�"+result.toString());
				}
			}
		}
		if("table".equals(action))
		{
			System.out.println("һ����������");
			TableUtil table = new TableUtil();
			if(table.createrTable("123456"))
			{System.out.println("����ɹ�");}else{System.out.println("����ʧ��");}
		}
		if("uptable".equals(action))
		{
			System.out.println("һ�����±�����");
			String up_data_json_string = request.getParameter("up_data_json_string");
			JSONObject up_data_json = new JSONObject(up_data_json_string);
			
			System.out.println("11111Ҫ���µ����ݣ�"+up_data_json.toString());
			DaoInf daoInf = new DaoInf();
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setAction("uptable");
			if(daoInf.runForTable(up_data_json))
			{
				System.out.println("���� ������ɣ����ɹ����أ�");
				responseMessage.setResult("pass");
			}else
			{
				System.out.println("���سɹ��������ݸ���ʧ�ܣ�");
				responseMessage.setResult("fail");
			}
			JSONObject result = new JSONObject(responseMessage);
			System.out.println("׼�����صĽ����"+result.toString());
			PrintWriter writer = response.getWriter();
			writer.write(result.toString());
			
			
			
			
		}
		if("get_all_db".equals(action))
		{
			System.out.println("��ȡ���ݿ������������󣡣�"+request.getParameter("db_id"));
			DaoInf daoInf = new DaoInf();
			JSONArray jsonArray = daoInf.getList(request.getParameter("db_id"), GcParameter.class,Store.class,In_store.class,Out_store.class,Summary.class);
			for (Object object : jsonArray) {
				System.out.println("��ȡ����������Ϊ��"+object.getClass().getSimpleName()+":"+object.toString());
			}
			PrintWriter writer = response.getWriter();
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setAction("get_all_db");
			responseMessage.setResult("pass");
			responseMessage.setJson_message(jsonArray);
			JSONObject resultjson= new JSONObject(responseMessage);
			System.out.println("׼�����صĽ��ǣ�"+resultjson.toString());
			writer.print(resultjson.toString());
		}

	}

	private boolean unLine(JSONObject jsonobject) {
		// TODO Auto-generated method stub
		try {
			int id=jsonobject.getInt("id");
			session= sessionUtil.getSession();
			Transaction t = session.beginTransaction();
			User user = session.get(User.class, jsonobject.getInt("id"));
			user.setLine(0);
			session.update(user);
			t.commit();
			System.out.println(user.toString());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		return false;
		}
	}

	/**
	 * �����û�����
	 * @param jsonobject Ҫ���µ��û���JSON
	 * @return �ɹ�true��ʧ��false
	 */
	private boolean upUserData(JSONObject jsonobject) {
		// TODO Auto-generated method stub
		try {
			int id=jsonobject.getInt("id");
			String user_name=jsonobject.getString("user_name");
			String signature=jsonobject.getString("signature");
			String photo_image = jsonobject.getString("photo_image");
			session= sessionUtil.getSession();
			Transaction t = session.beginTransaction();
			User user = session.get(User.class, jsonobject.getInt("id"));
			user.setUser_name(user_name);
			user.setSignature(signature);
			user.setPhotoImage(photo_image);
			session.update(user);
			t.commit();
			System.out.println(user.toString());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		return false;
		}
		
		
	}

	/**
	 * ͨ����������������û�
	 * @param id �û�ID
	 * @param user_psw �û�����
	 * @return ���ز��ҵ����û�User��û�ҵ�����Null
	 */
	private User findUserByNameAndPsw(int id, String user_psw) {
		// TODO Auto-generated method stub
		String hql="from User u where u.id="+id+" and u.user_psw='"+user_psw+"'";
		session = sessionUtil.getSession();
		List<User> list = session.createQuery(hql).getResultList();
		sessionUtil.close();
		if(!list.isEmpty())
		{
			
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * ע��һ���û�
	 * 
	 * @param user_json
	 *            Ҫע���û���JSON
	 */
	private JSONObject userRegedit(String user_json) {
		// TODO Auto-generated method stub
		JSONObject json_user = new JSONObject(user_json);
		User user = new User();

		if (!json_user.isNull("photo_image")) {
			String photo_image = json_user.getString("photo_image");
			user.setPhotoImage(json_user.getString("photo_image"));
			System.out.println("�����Ƭ" + photo_image);
		}
		if (!json_user.isNull("signature")) {
			String signature = json_user.getString("signature");
			user.setSignature(signature);
			System.out.println("���ǩ��" + signature);
		}
		if (!json_user.isNull("user_name")) {
			String user_name = json_user.getString("user_name");
			user.setUser_name(user_name);
			System.out.println("�������" + user_name);
		}
		if (!json_user.isNull("user_psw")) {
			String user_psw = json_user.getString("user_psw");
			user.setUser_psw(user_psw);
			System.out.println("������룺" + user_psw);
		}
		if (!json_user.isNull("gander")) {
			String gander = json_user.getString("gander");
			user.setGander(gander);
			System.out.println("����Ա�" + gander);
		}
		if (!json_user.isNull("qq")) {
			String qq = json_user.getString("qq");
			user.setQq(qq);
			System.out.println("���QQ��" + qq);
		}
		if (!json_user.isNull("hobbly")) {
			String hobbly = json_user.getString("hobbly");
			user.setHobbly(hobbly);
			System.out.println("��İ��ã�" + hobbly);
			user.setLine(1);
		}

		try {
			session = sessionUtil.getSession();
			Transaction t = session.beginTransaction();
			session.save(user);
			t.commit();
			session.refresh(user);
			sessionUtil.close();
			TableUtil table = new TableUtil();
			if(table.createrTable(user.getId()+""))
			{
				JSONObject  getfor_db_user_json =new JSONObject(user);
				System.out.println("ע����û����������"+getfor_db_user_json.toString());
				return getfor_db_user_json;
			}else
			{
				 t = session.beginTransaction();
				session.delete(user);
				t.commit();
				return null;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ͨ���û�����ѯ���������û���JSON
	 * 
	 * @param name
	 *            �û���
	 * @return �ɹ� JSONstring�� ʧ�� null
	 */
	private String getUserByName(String name) {
		JSONObject user_json;
		String qhl = "from User u where u.user_name='" + name + "'";
		session = sessionUtil.getSession();
		List<User> list = session.createQuery(qhl).list();
		if (list != null && !list.isEmpty()) {
			user_json = new JSONObject(list.get(0));
			sessionUtil.close();
			return user_json.toString();
		}
		// List<User> list = session.createQuery("from User").list();
		// System.out.println("���鵽"+list.size()+"�����ݣ�");
		sessionUtil.close();
		return null;

	}

	/**
	 * ��ȡͷ���б�
	 * 
	 * @param request
	 */
	private JSONObject getPhotoAddList(HttpServletRequest request) {
		ArrayList<String> list = new ArrayList<>();
		String add = request.getSession().getServletContext().getRealPath("/") + "photo_user";
		File f = new java.io.File(add);
		File[] flist = f.listFiles();
		for (File file : flist) {
			System.out.println(file.getPath());
			String s = file.getPath().substring(file.getPath().indexOf("datapackage"), file.getPath().length());
			System.out.println("~~~~~~~~~" + s);
			s = s.replace('\\', '/');
			list.add(s);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (String file : list) {
			System.out.println(file);
		}

		ResponseMessage message = new ResponseMessage();
		message.setAction("get_photo_add_list");
		message.setResult(flist.length != 0 ? "ok" : "fail");
		message.setJson_message(list);
		JSONObject resultmessage = new JSONObject(message);

		System.out.println("last\n" + resultmessage.toString());

		return resultmessage;

	}

}
