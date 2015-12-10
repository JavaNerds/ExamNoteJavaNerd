
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.nio.channels.FileChannel;

@WebServlet("/Upload")
public class Upload extends HttpServlet{	
	
	private Connection conn;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Upload(){
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//String filename = request.;//request.getParameter("fileName");
		String tst = request.getParameter("fileName");
				
		
		String databaseUsername = "";
	    String databasePassword = "";
	    String databaseFirstname = "";
		PrintWriter printWriter = response.getWriter();
		//*******sunit for file
	
		//******end
	
		
		conn = getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			String SQL = "SELECT * FROM user WHERE username='" + username + "' && password='" + password+ "'";
		
		    rs = stmt.executeQuery(SQL);
		   
		    
	        // Check Username and Password
		    while (rs.next()) {
		        databaseUsername = rs.getString("username");
		        databasePassword = rs.getString("password");
		        databaseFirstname = rs.getString("firstname");
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

		if(username.equalsIgnoreCase(databaseUsername) && password.equalsIgnoreCase(databasePassword)){	
			//printWriter.println("Hello "+ databaseFirstname+"! login successfull");
			try{
				stmt = conn.createStatement();
				String sql = "INSERT INTO user(username, password, firstname, FILES) VALUES ('sunit','sunit','sunit','"+inputStream+"')"; 
				//PreparedStatement statement = conn.prepareStatement(sql);
			    stmt.executeQuery(sql);
			
			printWriter.println("Hello "+ databaseFirstname+"! login successfull");
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			printWriter.println("username or password incorrect");
		}
		
	}
	//*****FileChannel method by sunit
	private static void copyFileUsingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	public Connection getConnection(){
        try{
            // Load the database driver
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        
        try{
            // Get a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/examnote?user=root&password=");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}