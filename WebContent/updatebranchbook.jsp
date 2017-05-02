<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	List<Book> bookList = new ArrayList<>();
	List<LibraryBranch> branchList = new ArrayList<>();
	
	Integer bookID = Integer.parseInt(request.getParameter("bookId"));
	Integer branchID = Integer.parseInt(request.getParameter("branchId"));
	String bookName = request.getParameter("bookName");
	String branchName = request.getParameter("branchName");
	Integer bookCount = Integer.parseInt(request.getParameter("bookCount"));
	
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
	<form action="addBookBranch" id="addbookbranchform" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Adding Book Entry for Branch:</p>
			Book Name: <input type="text" class="form-control" name="bookName" id="bookName" value="<%=bookName %>" size="50" readonly="readonly">
			Book ID: <input type="text" class="form-control" name="bookId" id="bookId" value="<%=bookID %>" size="50" readonly="readonly"><br />
			Branch Name: <input type="text" class="form-control" name="branchName" id="branchName" value="<%=branchName %>" size="50" readonly="readonly">
			Branch ID: <input type="text" class="form-control" name="branchId" id="branchId" value="<%=branchID %>" size="50" readonly="readonly"><br />
			Count to Add: <input type="number" class="form-control" name="newCount" id="newCount" placeholder="Input How Many Books to Add..." size="50" min='-<%=bookCount%>'><br />
			<input type="hidden" name="count" id="count" value='<%=bookCount%>' readonly="readonly">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>