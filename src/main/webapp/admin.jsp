

<%@page import="com.learn.mycart.helper.Helper"%>
<%@page import="com.learn.mycart.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.learn.mycart.helper.FactoryProvider"%>
<%@page import="com.learn.mycart.dao.CategoryDao"%>
<%@page import="com.learn.mycart.entities.User"%>
<%
User user = (User) session.getAttribute("current-user");
if (user == null) {
	session.setAttribute("message", "You are not logged in!! Login first");
	response.sendRedirect("login.jsp");
	return;

} else {

	if (user.getUserType().equals("normal")) {
		session.setAttribute("message", "You are not admin!! Do not access this page");
		response.sendRedirect("login.jsp");
		return;

	}
}
%>

<!-- To insert category dao -->
<%
	CategoryDao cdao=new CategoryDao(FactoryProvider.getFactory());
List<Category> list=cdao.getCategories();


//getting count

Map<String,Long> m=Helper.getCounts(FactoryProvider.getFactory());


%>




<!--If the user is null or the user us a normal user then below code will not be executed and simply the session will be returned to login page  -->





<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin panel</title>
<%@include file="components/common_css_js.jsp"%>

</head>
<body>
	<%@include file="components/navbar.jsp"%>

	<div class="container admin">
	
	<!-- To display message after category data is saved -->
	<div class="container-fluid  mt-3">
	    <%@include file="components/message.jsp" %>
	
	</div>
		<div class="row mt-3">

			<!-- first column -->
			<div class="col-md-4">

				<!-- First Box -->
				<div class="card">

					<div class="card-body text-center">

						<div class="container">
							<img style="max-width: 100px;" class="img-fluid rounded-circle"
								alt="user_icon" src="img/team.png">
						</div>

						<h1><%= m.get("userCount") %></h1>

						<h1 class="text-uppercase text-muted">Users</h1>


					</div>


				</div>




			</div>

			<!-- second column -->
			<div class="col-md-4">
				<!-- Second Box -->
				<div class="card">


					<div class="card-body text-center">

						<div class="container">
							<img style="max-width: 100px;" class="img-fluid rounded-circle"
								alt="user_icon" src="img/list.png">
						</div>

						<h1><%= list.size() %></h1>
						<h1 class="text-uppercase text-muted">Categories</h1>


					</div>


				</div>

			</div>

			<!-- third column -->
			<div class="col-md-4">
				<!--Third Box -->
				<div class="card">


					<div class="card-body text-center">

						<div class="container">
							<img style="max-width: 100px;" class="img-fluid rounded-circle"
								alt="user_icon" src="img/package.png">
						</div>

						<h1><%= m.get("productCount") %></h1>
						<h1 class="text-uppercase text-muted">Products</h1>


					</div>


				</div>
			</div>


		</div>

		<!-- second row -->
		<div class="row mt-4">

			<!-- box 1 -->
			<div class="col-md-6">
				<div class="card" data-toggle="modal"
					data-target="#add-category-modal">

					<div class="card-body text-center">

						<div class="container">
							<img style="max-width: 100px;" class="img-fluid rounded-circle"
								alt="user_icon" src="img/key.png">
						</div>

						<p class="mt-2">Click here to add new category</p>

						<h1 class="text-uppercase text-muted">Add Categories</h1>


					</div>


				</div>


			</div>

			<!-- box 2 -->
			<div class="col-md-6">
				<div class="card" data-toggle="modal" data-target="#add-product-modal">

					<div class="card-body text-center">

						<div class="container">
							<img style="max-width: 100px;" class="img-fluid rounded-circle"
								alt="user_icon" src="img/add.png">
						</div>

						<p class="mt-2">Click here to add new Product</p>


						<h1 class="text-uppercase text-muted">Add Product</h1>


					</div>


				</div>


			</div>

		</div>



	</div>

	<!-- add category modal -->


	<!-- Modal -->
	<div class="modal fade" id="add-category-modal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header custom-bg text-white">
					<h5 class="modal-title" id="exampleModalLabel">Fill Category
						Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="ProductOperationServlet" method="post">
					<input type="hidden" name="operation" value="addcategory">
					
					

						<div class="form-group">
							<input type="text" class="form-control" name="catTitle"
								placeholder="Enter category title" required />


						</div>

						<div class="form-group">
							<textarea style="height: 300px;" class="form-control"
								placeholder="Enter category description" name="catDescription"
								required></textarea>

						</div>

						<div class="container text-center">
							<button class="btn btn-outline-success">Add Category</button>
							<button type="button" class="btn btn-outline-success"
								data-dismiss="modal">Close</button>
						</div>
						
						
					</form>



				</div>

			</div>
		</div>
	</div>

	<!-- End add category modal -->

<!-- ######################################################################## -->




	<!-- Add product modal -->
	<!-- Modal -->
	<div class="modal fade" id="add-product-modal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header custom-bg text-white">
					<h5 class="modal-title" id="exampleModalLabel"> Product
						Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<!-- form starts here -->
					<form action="ProductOperationServlet" method="post" enctype="multipart/form-data">
					
					<input type="hidden" name="operation" value="addproduct"/>
					
                      <!-- Product title -->
						<div class="form-group">
							<input type="text" class="form-control" name="pName"
								placeholder="Enter product title" required />
						</div>
						
						
                       <!-- Product description -->
						<div class="form-group">
							<textarea style="height: 150px;" class="form-control"
								placeholder="Enter product description" name="pDesc"
								required></textarea>

						</div>

						

                        <!--  product price -->
						<div class="form-group">
							<input type="number" class="form-control" name="pPrice"
								placeholder="Enter product price" required />

                            </div>

                         <!-- product discount -->
						<div class="form-group">
							<input type="number" class="form-control" name="pDiscount"
								placeholder="Enter product discount " required />
                        </div>

						

                            <!-- product quantity -->
						<div class="form-group">
							<input type="number" class="form-control" name="pQuantity"
								placeholder="Enter product Quantity" required />


						</div>
						<!-- product category -->
						
						
						
						
						<div class="form-group">
							<select name="catId" class="form-control" id="">
								
								<%
								for(Category c:list){
								%>
								<option value="<%= c.getCategoryId() %>"><%= c.getCategoryTitle() %></option>
								
								<% }
								%>
							</select>
							
						</div>
						
						<!-- product file -->
						<div class="form-group">
						<label for="pPic">Select Picture of product</label>
						<br>
							<input type="file" id="pPic"  name="pPic"
								 required />


						</div>
						


                        <!-- submit button -->
						<div class="container text-center">
							<button class="btn btn-outline-success">Add Products</button>
							<button type="button" class="btn btn-outline-success"
								data-dismiss="modal">Close</button>
						</div>
						
						
					
					</form>
					
					<!-- form ends here -->
					
						<!-- footer -->
						



				</div>

			</div>
		</div>
	</div>



	<!-- End product modal -->
	
	<%@include file="components/common_modals.jsp" %>
</body>
</html>