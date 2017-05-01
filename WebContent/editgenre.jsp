<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer genreId = Integer.parseInt(request.getParameter("genreId"));
	String genreName = request.getParameter("genreName");
	//Author author = service.getAuthorByPk(authorId);
	//System.out.println("NAAAAME: "+authorName);
	//System.out.println("IDDDD: "+authorId);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Update Genre Details</h4>
	</div>
	<form action="editGenre" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Genre:</p>
			Genre Name: <input type="text" name="genreName" id="genreName" value="<%=genreName%>"><br />
			Genre ID: <input type="text" name="genreId" id="genreId" value="<%=genreId%>" readonly="readonly">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>