package com;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Data extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id,name;
	java.sql.Connection con;
	
	public void init(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bhanu","root","");
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    id=request.getParameter("id");
    name=request.getParameter("name");
    try {
		PreparedStatement statement=con.prepareStatement("insert into odc values(?,?)");
		statement.setString(1, id);
		statement.setString(2, name);
		int affect=statement.executeUpdate();
		if(affect==0)
		{
			RequestDispatcher dispatcher=request.getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
		}else
		{
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			out.println("<html><body><h1>data stored succesfully</h1><br><a href='/RBS_ODC/index.html'>HOME</a></body></html>");	
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
    
	
	}
	protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			Statement statement=con.createStatement();
			ResultSet res=statement.executeQuery("Select * from odc");
			out.println("<!DOCTYPE html><html><head><style>table,th,td{border:1px solid black;border-collapse:collapse;padding=10px}</style></head><body><table style:'width=100%'><tr><th>Emp_id</th><th>Emp_name</th><tr>");
			while(res.next())
			{
				out.println("<tr><td>"+res.getString(1)+"</td><td>"+res.getString(2)+"</td></tr>");	
			}
			out.println("</table></body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void destroy()
	{
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
