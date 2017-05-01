<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer authorId = Integer.parseInt(request.getParameter("authorId"));
	String authorName = request.getParameter("authorName");
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
		<h4 class="modal-title">Update Author Details</h4>
	</div>
	<form action="editAuthor" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Author:</p>
			Author Name: <input type="text" name="authorName" id="authorName" value="<%=authorName%>"><br />
			Author ID: <input type="text" name="authorId" id="authorId" value="<%=authorId%>" readonly="readonly">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>