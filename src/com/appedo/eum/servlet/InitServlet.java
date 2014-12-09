package com.appedo.eum.servlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appedo.eum.common.Constants;




/**
 * project initiation to set application parameters
 */

public class InitServlet extends HttpServlet {
	// set log access

	private static final long serialVersionUID = 1L;
	public static String realPath = null;
	public static String CONFIGFILEPATH = "";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() {
		// declare servlet context
		ServletContext context = getServletContext();
		realPath = context.getRealPath("//");		
		System.out.println("CONFIGFILEPATH="+Constants.CONFIGFILEPATH);
		
		try {
			Properties prop = new Properties();
     		InputStream is = new FileInputStream( Constants.CONFIGFILEPATH );
     		prop.load(is);
     		
     		// Har resource path 
     		Constants.HAR_FOLDER_PATH = prop.getProperty("RESOURCE_PATH");
     		
     		System.out.println("RESOURCE_PATH ="+Constants.HAR_FOLDER_PATH);
     		
     		
			
		}catch(Exception e) {
			System.out.println("Exception in init()"+e.getMessage());
			
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
	}
}
