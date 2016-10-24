package com.syz.dataPackage.servel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import com.sun.javafx.sg.prism.NGShape.Mode;

/**
 * Servlet implementation class upLoad
 */
public class upLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upLoad() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("收到一个上传请求！~~~~~~~~~~~");
		String uploadFilePath = this.getServletContext().getRealPath("user_data");
		File filePath = new File(uploadFilePath);
		if(!filePath.exists())
		{
			filePath.mkdir();
		}
		System.out.println("上传到的目录："+uploadFilePath);
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(102400, filePath);
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		String contentType = request.getContentType();
		System.out.println("请求表单类型："+contentType);
		try {
			 List<FileItem> list = servletFileUpload.parseRequest(request);
			 Iterator <FileItem> iter=list.iterator();
			 while(iter.hasNext())
			 {
				 FileItem fileitem = iter.next();
				 if(fileitem.isFormField())
				 {
					 String name = fileitem.getFieldName();
					 String vale = new String(fileitem.get(),"utf-8");
					 System.out.println("name:"+name+"\nvalues:"+vale);
				 }else{
					 System.out.println("上传的类型："+fileitem.getContentType());
					 //String fileName=new String(fileitem.getName().getBytes(),"utf-8");
					 String fileName=fileitem.getName();
					 System.out.println("上传的文件名称是："+fileName);
					 try {
						fileitem.write(new File(filePath,fileitem.getName()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 }
			 }
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
