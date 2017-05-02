<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	LibrarianService service = new LibrarianService();
	List<Book> bookList = new ArrayList<>();
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer userId = -3; //neither admin nor borrower if = -3. 0 is admin.
	userId = (Integer) session.getAttribute("userId");
	if(userId == null) {
		userId = -3;
	}
	if (request.getAttribute("bookBorrowList") != null) {
		bookList = (List<Book>) request.getAttribute("bookborrowList");
	} else {
		bookList = service.getBooksNotInBranch(branchId);
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
	<form action="addNewBookBranch" id="addbookbrform" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the Details of a New Book to Add:</p>
			<br/>	
			<label for="selauth">Select the Book to Add to the Library:</label>
			<select class="form-control" name="booklist" form="addbookbrform" id="selbook">
			<%for(Book b: bookList) { %>
				<option value="<%=b.getBookId() %>"><%=b.getBookId()%>) <%=b.getTitle() %></option>
			<%} %>
			</select><br/>
			Count to Add: <input type="number" class="form-control" name="count" id="count" placeholder="Input How Many Books to Add..." size="50" min='0'><br />
			<input type="hidden" name="branchId" id="branchId" value='<%=branchId%>' readonly="readonly">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>