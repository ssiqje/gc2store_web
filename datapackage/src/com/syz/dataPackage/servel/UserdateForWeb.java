package com.syz.dataPackage.servel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
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

/**
 * Servlet implementation class UserdateForWeb
 */
public class UserdateForWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String action;
   	private SessionUtil sessionUtil;
   	private Session session;

   	/**
   	 * @see HttpServlet#HttpServlet()
   	 */
   	public UserdateForWeb() {
   		super();
   		sessionUtil = new SessionUtil();
   		// TODO Auto-generated constructor stub
   	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		action = request.getParameter("action");
		//OutputStream out = response.getOutputStream();
		
		System.out.println("userDateForWeb:"+action);
		JSONObject result=null;

		if ("login_user".equals(action)) 
		{
			String user_id =request.getParameter("user_id");
			String user_psw = request.getParameter("user_psw");
			if(user_id!=null&&!"".equals(user_id)&&user_psw!=null&&!"".equals(user_psw))
			{
				int id = 0;
				try {
					id = Integer.parseInt(user_id);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					ResponseMessage message = new ResponseMessage();
					message.setAction("login_user");
					message.setResult("fail");
					message.setJson_message("用户ID或密码错误！");
					result = new JSONObject(message);
					
					System.out.println("返回的结果是："+result.toString());
					e.printStackTrace();
				}
				User user = findUserByNameAndPsw(id, user_psw);
				if(user!=null)
				{
					if(user.getLine()==1)
					{
						ResponseMessage message = new ResponseMessage();
						message.setAction("login_user");
						message.setResult("fail");
						message.setJson_message("你已在其他设备上登入了！");
						result = new JSONObject(message);
						//out.write(result.toString().getBytes());
						System.out.println("返回的结果是："+result.toString());
					}else
					{
						ResponseMessage message = new ResponseMessage();
						message.setAction("login_user");
						message.setResult("pass");
						message.setJson_message(new JSONObject(user).toString());
						result = new JSONObject(message);
						//out.write(result.toString().getBytes());
						//更改用户在线状态
						user.setLine(1);
						//更新数据库
						session = sessionUtil.getSession();
						Transaction t = session.beginTransaction();
						session.update(user);
						t.commit();
						System.out.println("更改后的用户："+user.toString());
						System.out.println("返回的结果是："+result.toString());
					}
				}else
				{
					ResponseMessage message = new ResponseMessage();
					message.setAction("login_user");
					message.setResult("fail");
					message.setJson_message("用户ID或密码错误！");
					result = new JSONObject(message);
					System.out.println("返回的结果是："+result.toString());
				}
			}else
			{
				ResponseMessage message = new ResponseMessage();
				message.setAction("login_user");
				message.setResult("fail");
				message.setJson_message("用户ID或密码错误！");
				result = new JSONObject(message);
				System.out.println("返回的结果是："+result.toString());
			}
			request.setAttribute("resultJson", result);
			request.getRequestDispatcher("/islogin/islogin_pass.jsp").forward(request, response);
		}
		if(action.startsWith("savetablerecord"))
		{
			if(action.endsWith("in_store"))
			{return;}
			if(action.endsWith("out_store"))
			{
				
				return;}
		}
		if(action.startsWith("gettable"))
		{
			DaoInf dao = new DaoInf();
			System.out.println("收到一个获取表的请求"+action.substring(0, action.indexOf("_")));
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			System.out.println("获取的Session:"+session);
			JSONArray jsonarray=null;
			JSONArray parameter=null;
			String table=action.substring(action.indexOf("_")+1, action.length());
			if("summary".equals(table))
			{
				jsonarray = dao.getList(user.getId()+"", Summary.class);
				request.setAttribute("table", jsonarray);
				request.getRequestDispatcher("/user_main/user_main.jsp").forward(request, response);
				return;
			}
			if("store".equals(table))
			{
				jsonarray = dao.getList(user.getId()+"", Store.class);
				request.setAttribute("table", jsonarray);
				request.getRequestDispatcher("/user_main/store_table.jsp").forward(request, response);
				return;
			}
			if("in_store".equals(table))
			{
				jsonarray = dao.getList(user.getId()+"", In_store.class);
				request.setAttribute("table", jsonarray);
				parameter = dao.getList(user.getId()+"", GcParameter.class);
				request.setAttribute("parameter", parameter);
				request.getRequestDispatcher("/user_main/in_store_table.jsp").forward(request, response);
				return;
			}
			if("out_store".equals(table))
			{
				jsonarray = dao.getList(user.getId()+"", Out_store.class);
				request.setAttribute("table", jsonarray);
				
				parameter = dao.getList(user.getId()+"", GcParameter.class);
				request.setAttribute("parameter", parameter);
				request.getRequestDispatcher("/user_main/out_store_table.jsp").forward(request, response);
				return;
			}
			if("gcparameter".equals(table))
			{
				jsonarray = dao.getList(user.getId()+"", GcParameter.class);
				request.setAttribute("table", jsonarray);
				request.getRequestDispatcher("/user_main/gcparameter_table.jsp").forward(request, response);
				return;
			}
			
		}
		if("logout".equals(action))
		{
			User user = (User) request.getSession(false).getAttribute("user");
				user.setLine(0);
				DaoInf dao = new DaoInf();
				dao.unline(user);
				request.getSession(false).setMaxInactiveInterval(0);
				request.getRequestDispatcher("/user_main/user_main.jsp").forward(request, response);
		}
		if("download".equals(action))
		{
			File file = new File(request.getSession().getServletContext().getRealPath("/")+"app/store.apk");
			if(!file.exists())
			{
				request.setAttribute("result", "文件不存在，请联系站长！");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
			InputStream inputStream = new FileInputStream(file);
			response.setContentType("application/vnd.android.package-archive");
			response.setHeader("Content-Disposition", "attachment;filename=\"store.apk\"");//4,230,698
			response.setHeader("Content-Length", file.length()+"");
			ServletOutputStream  out = response.getOutputStream();
			byte[] b =new byte[1024*50];
			int i=-1;
			while((i=inputStream.read(b))!=-1)
			{
				out.write(b, 0, i);
			}
			inputStream.close();
			out.close();
		}
			
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/**
	 * 通过姓名和密码查找用户
	 * @param id 用户ID
	 * @param user_psw 用户密码
	 * @return 返回查找到的用户User，没找到返回Null
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

}
