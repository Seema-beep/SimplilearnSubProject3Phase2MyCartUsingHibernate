package com.learn.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.learn.mycart.dao.UserDao;
import com.learn.mycart.entities.User;
import com.learn.mycart.helper.FactoryProvider;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//coding area
		try {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//validations for email and password in java
			/*if(email.equals("seemabhuyan2020@gmail.com") && password.equals("manvik")) {
				
				HttpSession session = request.getSession();
				session.setAttribute("Email", email);
				response.sendRedirect("index.jsp");
			}
			
			else
			{
				response.sendRedirect("login.jsp");
			}*/
			
			//authenticating user
			UserDao userDao = new UserDao(FactoryProvider.getFactory());
			User user = userDao.getUserByEmailAndPassword(email, password);
			//System.out.println(user);
			
			HttpSession httpsession = request.getSession();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(user==null)
			{
				//out.println("<h1> Invalid Details </h1>");
				httpsession.setAttribute("message", "Invalid Details!! Try with another one");
				response.sendRedirect("login.jsp");
				return;
			}
			else {
				out.println("<h1> Welcome" +user.getUserName()+ "</h1>");
			
			
			//login
			httpsession.setAttribute("current-user", user);
			if(user.getUserType().equals("admin"))
			{
				response.sendRedirect("admin.jsp");
				//admin.jsp...if the user is administrator
			
			}
			 if(user.getUserType().equals("normal")) {
				response.sendRedirect("normal.jsp");
				//normal.jsp...if the user is normal user
			}
			else {
				out.println("We have not identified user type");
			}
			
			
			
			
			}	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}

