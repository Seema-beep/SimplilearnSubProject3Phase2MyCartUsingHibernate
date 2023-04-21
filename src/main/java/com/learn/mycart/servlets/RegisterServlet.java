package com.learn.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learn.mycart.entities.User;
import com.learn.mycart.helper.FactoryProvider;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
    
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//firstly servlets take all the data from the form and store them respectively in variables
			try {
				String userName = request.getParameter("user_name");
				String userEmail = request.getParameter("user_email");
				String userPassword = request.getParameter("user_password");
				String userPhone = request.getParameter("user_phone");
				String userAddress = request.getParameter("user_address");
				
				//validations
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				if(userName.isEmpty()) {
					out.println("Name is blank");
					return;
				}
				
				if(userEmail.isEmpty()) {
					out.println("Email address is blank");
					return;
				}
					
				
				if(userPassword.isEmpty()) {
					out.println("Provide the passwords");
					return;
				}
				
				if(userPhone.isEmpty()) {
					out.println("Provide the phonenumber");
					return;
				}
				
				if(userAddress.isEmpty()) {
					out.println("Fill the address");
					return;
				}
				
				
				//next task is creating user object to store data that is in the variables
				 User user = new User(userName, userEmail, userPassword, userPhone,"default.jpg", userAddress,"normal");
				 
				 Session hibernateSession = FactoryProvider.getFactory().openSession();
				//task is to save the data in database
				 Transaction tx = hibernateSession.beginTransaction();
				 Serializable userId = hibernateSession.save(user);
				 
				 
					
				
				 tx.commit();
				 hibernateSession.close();
				 
				 
				 HttpSession httpSession = request.getSession();
				 httpSession.setAttribute("message", "Registration Successful!!! User Id is"+userId);
				 response.sendRedirect("register.jsp");
				 return;
				 
				 
				// out.println("successfully saved");
				// out.println("<br>User id is "+userId);
					
					
				
				
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

}
