<%
	Integer borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
	String borrowerName = request.getParameter("borrowerName");
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	String branchName = request.getParameter("branchName");
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	String bookName = request.getParameter("bookName");
	
	String dateOut = request.getParameter("dateOut");
	String dateDue = request.getParameter("dateDue");
	String dateIn = request.getParameter("dateIn");
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Update Borrower Details</h4>
	</div>
	<form action="editBookLoan" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the Borrower:</p>
			Borrower Name: <input type="text" class="form-control" name="borrowerName" id="borrowerName" value="<%=borrowerName%>" readonly="readonly">
			Borrower ID: <input type="text" class="form-control" name="borrowerId" id="borrowerId" value="<%=borrowerId%>" readonly="readonly"><br />
			Branch Name: <input type="text" class="form-control" name="branchName" id="branchName" value="<%=branchName%>" readonly="readonly">
			Branch ID: <input type="text" class="form-control" name="branchId" id="branchId" value="<%=branchId%>" readonly="readonly"><br />
			Book Name: <input type="text" class="form-control" name="bookName" id="bookName" value="<%=bookName%>" readonly="readonly">
			Book ID: <input type="text" class="form-control" name="bookId" id="bookId" value="<%=bookId%>" readonly="readonly"><br />
			Date Checked Out: <input type="text" class="form-control" name="dateOut" id="dateOut" value="<%=dateOut%>" readonly="readonly"><br />
			Due Date        : <input title='Please Type Year-Month-Day Hr:Min:Sec In YYYY-MM-DD hh:mm:ss Format'
				type="text" class="form-control" name="dateDue" id="dateDue" placeholder="YYYY-MM-DD hh:mm:ss"
				value="<%=dateDue%>" pattern="[0-9]{4}[-][0-9]{2}[-][0-9]{2} [0-9]{2}[:][0-9]{2}[:][0-9]{2}"><br />
			Date Checked In : <input title='Please Type Year-Month-Day Hr:Min:Sec In YYYY-MM-DD hh:mm:ss Format'
				type="text" class="form-control" name="dateIn" id="dateIn" placeholder="YYYY-MM-DD hh:mm:ss"
				value="<%=dateIn%>" pattern="[0-9]{4}[-][0-9]{2}[-][0-9]{2} [0-9]{2}[:][0-9]{2}[:][0-9]{2}"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>