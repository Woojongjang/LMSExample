<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	String branchName = request.getParameter("branchName");
	String branchAddr = request.getParameter("branchAddr");
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Update Library Branch Details</h4>
	</div>
	<form action="editBranch" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Branch:</p>
			Branch Name: <input type="text" class="form-control" name="branchName" id="branchName" value="<%=branchName%>"><br />
			Branch ID: <input type="text" class="form-control" name="branchId" id="branchId" value="<%=branchId%>" readonly="readonly">
			Branch Address: <input type="text" class="form-control" name="branchAddr" id="branchAddr" value="<%=branchAddr%>"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>