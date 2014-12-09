package com.appedo.eum.har;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appedo.eum.common.Constants;

/**
 * Servlet implementation class HarManager
 */
@WebServlet("/HarManager")
public class HarManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HarManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			doDelete(request, response);
			// sends response to client
			response.getWriter().print("success");
		}catch(Exception e) {
			response.getWriter().print("failure");
		}
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String strTestId = request.getHeader("test_id");
		
		if(strTestId.trim().length()>0 ) {
			File fHarrepoFolderPath = new File(Constants.HAR_FOLDER_PATH+File.separator+strTestId);
			if (fHarrepoFolderPath.exists()) {
				
				try {
					delete(fHarrepoFolderPath);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * This is to delete the har files from har repository
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		
		if(file.isDirectory()) {
			
			//directory is empty, then delete it
			if(file.list().length==0) {
				file.delete();
				System.out.println("Directory is deleted : " + file.getAbsolutePath());
			}else {
				
				//list all the directory contents
				String files[] = file.list();
				
				for (String temp : files) {
					//construct the file structure
					File fileDelete = new File(file, temp);
					
					//recursive delete
					delete(fileDelete);
				}
				//check the directory again, if empty then delete it
				if(file.list().length==0) {
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		}else{
			//if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

}
