package com.learn.mycart.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.learn.mycart.dao.CategoryDao;
import com.learn.mycart.dao.ProductDao;
import com.learn.mycart.entities.Category;
import com.learn.mycart.entities.Product;
import com.learn.mycart.helper.FactoryProvider;


@MultipartConfig
public class ProductOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//servlet2
		//add category
		//add product
		
		try {
			String op = request.getParameter("operation");
			if(op.trim().equals("addcategory"))
			{
				//fetching category data
				String title = request.getParameter("catTitle");
				String description = request.getParameter("catDescription");
				
				Category category = new Category();
				category.setCategoryTitle(title);
				category.setCategoryDescription(description);
				
				//next step to save the category in database via the dao layer after creating CategoryDao
				
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				
				CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
				int catId = categoryDao.saveCategory(category);
				
				//out.println("Category Saved");
				//This coding is to send message
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("message", "Category added successfully:"+catId);
				response.sendRedirect("admin.jsp");
				return;
				
				
				
				
				
				
				
				
				
			}
			else if(op.trim().equals("addproduct")) {
				//add product
				//add work
				String pName = request.getParameter("pName");
				String pDesc = request.getParameter("pDesc");
				int pPrice = Integer.parseInt(request.getParameter("pPrice"));
				int pDiscount = Integer.parseInt(request.getParameter("pDiscount"));
				int pQuantity = Integer.parseInt(request.getParameter("pQuantity"));
				int catId = Integer.parseInt(request.getParameter("catId"));
				Part part = request.getPart("pPic");
				
				
				
				Product p = new Product();
				p.setpName(pName);
				p.setpDesc(pDesc);
				p.setpPrice(pPrice);
				p.setpDiscount(pDiscount);
				p.setpQuantity(pQuantity);
				p.setpPhoto(part.getSubmittedFileName());
				
				//get category by id
				CategoryDao cdoa = new CategoryDao(FactoryProvider.getFactory());
				Category category = cdoa.getCategoryById(catId);
				 
				p.setCategory(category);
				
				//product save....
				
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
				pdao.saveProduct(p);
				
				
				//pic upload
				//find out the path to upload photo
				String path = request.getRealPath("img") + File.separator+"products"+File.separator+part.getSubmittedFileName();
				System.out.println(path);
				
				//uploading code
				
				try {
				FileOutputStream fos=new FileOutputStream(path);
				InputStream is=part.getInputStream();
				
				//reading data
				byte []data=new byte[is.available()];
				is.read(data);
				
				
				//writing the data
				fos.write(data);
				fos.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				out.println("Product saved successfully....");
				
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("message", "Product is added successfully:");
				response.sendRedirect("admin.jsp");
				return;
				
				
				
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		
		
	}

}
