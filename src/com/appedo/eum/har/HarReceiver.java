package com.appedo.eum.har;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appedo.eum.common.Constants;

/**
 * Servlet implementation class HarReceiver
 */
@WebServlet("/HarReceiver")
public class HarReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final int BUFFER_SIZE = 4096;
	StringBuilder sb=new StringBuilder();
	FileInputStream inputStream = null;
	OutputStream outputStream = null;
	BufferedReader reader = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HarReceiver() {
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
		// TODO Auto-generated method stub
		
		try {
			doImport(request, response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void doImport(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	
		try {
			
			// Get har file name , testid from the  HTTP header
			
			String strHarFileName = request.getHeader("har_file_Name");
			String strTestId = request.getHeader("test_id");
			
			if(strTestId.trim().length()>0 && strHarFileName.trim().length()>0) {
				
				File fHarrepoFolderPath = new File(Constants.HAR_FOLDER_PATH+File.separator+strTestId);
				if (!fHarrepoFolderPath.exists()) {
					if (fHarrepoFolderPath.mkdir()) {
						System.out.println("Directory is created!");
					} else {
						System.out.println("Failed to create directory!");
					}
				}
				
				File saveFile = new File(Constants.HAR_FOLDER_PATH+File.separator+strTestId+File.separator+strHarFileName);
				// prints out all header values
				System.out.println("===== Begin headers =====");
				Enumeration<String> names = request.getHeaderNames();
				while (names.hasMoreElements()) {
					String headerName = names.nextElement();
					System.out.println(headerName + " = " + request.getHeader(headerName));
				}
				System.out.println("===== End headers =====\n");
				
				// opens input stream of the request for reading data
				InputStream inputStream = request.getInputStream();
				// opens an output stream for writing file
				FileOutputStream outputStream = new FileOutputStream(saveFile);
				
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				System.out.println("Receiving data...");
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				System.out.println("Data received.");
				outputStream.close();
				inputStream.close();
				
				System.out.println("File written to: " + saveFile.getAbsolutePath());
				System.out.println("UPLOAD DONE");
				
				// sends response to client
				response.getWriter().print("success");
			}else {
				// sends response to client
				response.getWriter().print("failure");
			}
		}catch(Throwable t) {
			System.out.println("Exception in doUpload()"+t.getMessage());
			throw t;
		}
	}
}
