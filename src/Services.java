package com.GetNotes;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//@WebServlet(description = "FileUploadServlet Description", urlPatterns = { "/FileUploadServlet" })
/**
 * Servlet implementation class Services
 */
@WebServlet("/Services")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Services extends HttpServlet {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/upload_DB";
    private String dbUser = "root";
    private String dbPass = "";
    //private Statement statement = null;

    Connection conn = null; // connection to the database
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        //String firstName = request.getParameter("firstName");
        //String lastName = request.getParameter("lastName");
         
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("fileName");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
         
      
        String message = null;  // message will be sent back to client
         
        try {
            // connects to the database
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        	//Class.forName("com.mysql.jdbc.Driver");
        	Connection con = getConnection();
        	conn = con;//DriverManager.getConnection("jdbc:mysql://localhost:3306/uploads_DB?user=root&password=");
            // constructs SQL statement
            String sql = "INSERT INTO uploads_Table (FILES) values (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setString(1, firstName);
            //statement.setString(2, lastName);
             
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                statement.setBlob(1, inputStream);
            }
 
            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            request.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uploads_DB?user=root&password=");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}