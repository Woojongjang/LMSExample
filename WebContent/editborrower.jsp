<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
	String borrowerName = request.getParameter("borrowerName");
	String borrowerAddr = request.getParameter("borrowerAddr");
	String borrowerPhone = request.getParameter("borrowerPhone");
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Update Borrower Details</h4>
	</div>
	<form action="editBorrower" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Borrower:</p>
			Borrower Name: <input type="text" class="form-control" name="borrowerName" id="borrowerName" value="<%=borrowerName%>"><br />
			Borrower ID: <input type="text" class="form-control" name="borrowerId" id="borrowerId" value="<%=borrowerId%>" readonly="readonly">
			Borrower Address: <input type="text" class="form-control" name="borrowerAddr" id="borrowerAddr" value="<%=borrowerAddr%>"><br />
			Borrower Phone: <input type="text" class="form-control" name="borrowerPhone" id="borrowerPhone" value="<%=borrowerPhone%>"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>