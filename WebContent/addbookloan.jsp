<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	List<Book> bookList = new ArrayList<>();
	List<LibraryBranch> branchList = new ArrayList<>();
	List<Borrower> borrowList = new ArrayList<>();
	
	if(request.getParameter("borrowerId")!=null) {
		Integer borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
	}
	String borrowerName = request.getParameter("borrowerName");
	
	Integer userId = -3; //neither admin nor borrower if = -3. 0 is admin.
	userId = (Integer) session.getAttribute("userId");
	if(userId == null) {
		userId = -3;
	}
	if (request.getAttribute("bookBorrowList") != null) {
		bookList = (List<Book>) request.getAttribute("bookborrowList");
	} else {
		bookList = service.getAllBooks(null);
	}
	if (request.getAttribute("bookBranchList") != null) {
		branchList = (List<LibraryBranch>) request.getAttribute("bookBranchList");
	} else {
		branchList = service.getAllBranches(null);
	}
	if (request.getAttribute("bookBrwUserList") != null) {
		borrowList = (List<Borrower>) request.getAttribute("bookBrwUserList");
	} else {
		borrowList = service.getAllBorrowers(null);
	}
	//System.out.println(userId);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Book Details</h4>
	</div>
	<form action="addBookLoan" id="addbookloanform" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the Details of the Book to Borrow:</p>
			<br/>	
			<label for="selauth">Select the Book to Borrow:</label>
			<select class="form-control" name="booklist" form="addbookloanform" id="selbook">
			<%for(Book b: bookList) { %>
				<option value="<%=b.getBookId() %>"><%=b.getTitle() %></option>
			<%} %>
			</select>
			<label for="selpub">Select the Branch to Borrow from:</label>
			<select class="form-control" name="branchlist" form="addbookloanform" id="selbran">
				<%for(LibraryBranch lb: branchList) { %>
					<option value="<%=lb.getBranchId()%>"><%=lb.getBranchName() %></option>
				<%} %>
			</select>
			<%if(userId!=-3 && userId ==0) {%>
			<label for="selgenr">Select the User that's Borrowing the Book:</label>
			<select class="form-control" name="borrowerlist" form="addbookloanform" id="seluser">
				<%for(Borrower brw: borrowList) { %>
					<option value="<%=brw.getBorrowerId()%>"><%=brw.getBorrowerName() %></option>
				<%} %>
			</select>
			<%}
			  else {%>
			  <input type="text"name="borrowerId" id="borrowerId" value="<%=userId%>" readonly="readonly">
			  <%}%>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>