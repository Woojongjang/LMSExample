<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.BookLoan"%>
<%@page import="com.gcit.lms.service.BookLoanService"%>


<%
	BookLoanService service = new BookLoanService();
	Integer loanCount = service.getBookLoansCount();
	Integer numOfPages = 0;
	if (loanCount % 10 > 0) {
		numOfPages = loanCount / 10 + 1;
	} else {
		numOfPages = loanCount / 10;
	}
	List<BookLoan> loans = new ArrayList<>();
	if (request.getAttribute("loans") != null) {
		loans = (List<BookLoan>) request.getAttribute("loans");
	} else {
		loans = service.getAllBookLoans(1);
	}
%>
<script>
	function searchBookLoans(pageNum){
		$.ajax({
			url: "searchBookLoans",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#loansTable').html(data.key1)
		})
		.fail(function(data) {
		    alert('IT FAILED');
		});
	}
</script>

<div>
	<div>
		<%
			if (request.getAttribute("message") != null) {
		%>
		<div class="alert alert-info" role="alert">
			<strong>UPDATED</strong> ${message}
		</div>
		<%
			}
		%>
		<%
			if (request.getAttribute("deleteMsg") != null) {
		%>
		<div class="alert alert-danger" role="alert">
			<strong>DELETED</strong> ${deleteMsg}
		</div>
		<%
			}
		%>
		<%
			if (request.getAttribute("addMsg") != null) {
		%>
		<div class="alert alert-success" role="alert">
			<strong>ADDED</strong> ${addMsg}
		</div>
		<%
			}
		%>
		<div class="page-header">
			<h1>List of Book Loans in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editBookLoanModal"
			href="addbookloan.jsp">Add New Book Loan</button><br/><br/>
		<div>
			<form action="searchBookLoans">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchBookLoans(1);">
			</form>
		</div>
	
		<nav aria-label="Page navigation">
			<ul class="pagination" id="paginateId">
			<%
				String pageString = request.getParameter("pageNo");
				Integer pageNo = 1;
				if(pageString != null) {
					pageNo = Integer.parseInt(request.getParameter("pageNo"));
				}
				if(pageNo != 1) {
			%>
				<li><a href="pageBookLoans?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchBookLoans(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageBookLoans?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageBookLoans?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div>
			<table class="table table-striped" id="loansTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Borrower Name</th>
						<th>Borrower ID</th>
						<th>Branch Name</th>
						<th>Book Name</th>
						<th>Checked OUT</th>
						<th>Due Date</th>
						<th>Checked IN</th>
						<th>User Loans</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (BookLoan bl : loans) {
							String borrowerName = bl.getBorrower().getBorrowerName();
							Integer borrowerID = bl.getBorrower().getBorrowerId();
							String branchName = bl.getBranch().getBranchName();
							Integer branchID = bl.getBranch().getBranchId();
							String bookName = bl.getBook().getTitle();
							Integer bookID = bl.getBook().getBookId();
							String dateOut = bl.getDateChecked();
							String dateDue = bl.getDateDue();
							String dateIn = bl.getDateIn();
							Integer in = 1;
							if(dateDue==null) {
								dateDue = "NO DUE DATE";
							}
							if(dateIn==null) {
								dateIn = "NOT CHECKED IN";
								in = 0;
							}
							if(dateOut==null) {
								dateOut = "NOT CHECKED OUT?";
							}
							String borrowerNameEnc = URLEncoder.encode(borrowerName, "UTF-8");
							String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
							String bookNameEnc = URLEncoder.encode(bookName, "UTF-8");
							String dateOutEnc = URLEncoder.encode(dateOut, "UTF-8");
							String dateDueEnc = URLEncoder.encode(dateDue, "UTF-8");
							String dateInEnc = URLEncoder.encode(dateIn, "UTF-8");
							
					%>
					<tr>
						<td><%=loans.indexOf(bl) + 1%></td>
						<td><%=borrowerName%></td>
						<td><%=borrowerID%></td>
						<td><%=branchName%></td>
						<td><%=bookName%></td>
						<td><%=dateOut%></td>
						<td><%=dateDue%></td>
						<%if(in == 1) {%>
						<td><%=dateIn%></td>
						<%} %>
						<%if(in == 0) {%>
						<td style='color:red'><%=dateIn%></td>
						<%} %>
						<td><a href='borrowerloan.jsp?borrowerId=<%=borrowerID%>' class='btn btn-info' style='color:white'>Loans</a></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editBookLoanModal"
								href="editbookloan.jsp?borrowerId=<%=borrowerID%>&amp;borrowerName=<%=borrowerNameEnc%>
								&amp;branchId=<%=branchID%>&amp;branchName=<%=branchNameEnc%>
								&amp;bookId=<%=bookID%>&amp;bookName=<%=bookNameEnc%>
								&amp;dateOut=<%=dateOutEnc%>&amp;dateDue=<%=dateDueEnc%>&amp;dateIn=<%=dateInEnc%>">Update</button></td>
						<td><form action="deleteBookLoan" method="post">
							<input type="hidden" name="bookId" id="bookId" value="<%=bookID%>">
							<input type="hidden" name="borrowerId" id="borrowerId" value="<%=borrowerID%>">
							<input type="hidden" name="branchId" id="branchId" value="<%=branchID%>">
							<input type="hidden" name="dateOut" id="dateOut" value="<%=dateOut%>">
							<button  class="btn btn-danger">Delete</button>
							</form></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="modal fade" id="editBookLoanModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">....</div>
	</div>
</div>
<script>
$(document).ready(function()
{
    $('.modal').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
    }) ;
});
</script>