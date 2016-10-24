package com.syz.dataPackage.session_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionUtil 
{
	private Configuration configuration;
	private SessionFactory sessionFactory;
	private Session session;
	private Connection connection;
	
	
	/**
	 * ªÒ»°     Session
	 * @return Session
	 */
	public Session getSession()
	{
		configuration= new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
		return session;
	}
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql:///data_package", "root", "s17dmysql,cncg!");
			return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void closeConnection()
	{
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void close()
	{
		if(session!=null)
		{
			session.close();
		}
		if(sessionFactory!=null)
		{
			sessionFactory.close();
		}
		
	}
	
}
