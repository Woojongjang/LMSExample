<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
	String publisherName = request.getParameter("publisherName");
	String publisherAddr = request.getParameter("publisherAddr");
	String publisherPhone = request.getParameter("publisherPhone");
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
		<h4 class="modal-title">Update Publisher Details</h4>
	</div>
	<form action="editPublisher" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Publisher:</p>
			Publisher Name: <input type="text" class="form-control" name="publisherName" id="publisherName" value="<%=publisherName%>"><br />
			Publisher ID: <input type="text" class="form-control" name="publisherId" id="publisherId" value="<%=publisherId%>" readonly="readonly">
			Publisher Address: <input type="text" class="form-control" name="publisherAddr" id="publisherAddr" value="<%=publisherAddr%>"><br />
			Publisher Phone: <input type="text" class="form-control" name="publisherPhone" id="publisherPhone" value="<%=publisherPhone%>"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>