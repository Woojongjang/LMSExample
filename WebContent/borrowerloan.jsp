<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.BookLoan"%>
<%@page import="com.gcit.lms.service.BookLoanService"%>
<%
//System.out.println(request.getRequestURI());
//show view of all of borrower's loans
	Integer userId = -3; //neither admin nor borrower if = -3. 0 is admin.
	userId = (Integer) session.getAttribute("userId");
	Integer borrowerId = 0;
	if(userId == null) {
		userId = -3; //user is error: no one?
	}
	if(userId == 0) { //user is admin
		borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
	}
	else { //user is someone else
		if(userId > 0) {
			borrowerId = userId;
		}
		else {
			borrowerId = null; // user is probably a librarian (likely not)
		}
	}
	BookLoanService service = new BookLoanService();
	BorrowerService brwServ = new BorrowerService();
	String userName = "NO NAME";
	Borrower borrower = new Borrower();
	if(borrowerId!=null) {
		borrower = brwServ.getBorrowerById(borrowerId);
	}
	userName = borrower.getBorrowerName();
	
	Integer loanCount = service.getBookLoansCount(borrowerId);
	Integer numOfPages = 0;
	if (loanCount % 10 > 0) {
		numOfPages = loanCount / 10 + 1;
	} else {
		numOfPages = loanCount / 10;
	}
	List<BookLoan> loans = new ArrayList<>();
	
	if (request.getAttribute("borrowerloans") != null) {
		loans = (List<BookLoan>) request.getAttribute("borrowerloans");
	} else {
		loans = service.getUserBookLoans(1, borrower);
	}
%>
<script>
	function searchBorrowerLoans(pageNum){
		$.ajax({
			url: "searchBorrowerLoans",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#borrowerLoansTable').html(data.key1)
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
			<h1>List of Book Loans for User: <%=userName %></h1>
		</div>
		<a type="button" class="btn btn-success"
			href="choosebranch.jsp">Borrow a New Book</a><br/><br/>
		<div>
			<form action="searchBorrowerLoans">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchBorrowerLoans(1);">
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
				<li><a href="pageBorrowerLoans?pageNo=<%=pageNo-1 %>&amp;borrowerId=<%=borrowerId %>" aria-label="Previous" onclick="searchBookLoans(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageBorrowerLoans?pageNo=<%=i%>&amp;borrowerId=<%=borrowerId %>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageBorrowerLoans?pageNo=<%=pageNo+1 %>&amp;borrowerId=<%=borrowerId %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div>
			<table class="table table-striped" id="borrowerLoansTable">
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
						<th>Return Book</th>
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
						<td><%=dateIn%></td>
						<%if(in == 0) {%>
						<td><form action="bookLoanReturn" method="post">
							<input type="hidden" name="bookId" id="bookId" value="<%=bookID%>">
							<input type="hidden" name="borrowerId" id="borrowerId" value="<%=borrowerID%>">
							<input type="hidden" name="branchId" id="branchId" value="<%=branchID%>">
							<input type="hidden" name="dateOut" id="dateOut" value="<%=dateOut%>">
							<input type="hidden" name="dateIn" id="dateIn" value="<%=dateIn%>">
							<button  class="btn btn-warning">Return</button>
							</form></td>
						<%} %>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="modal fade" id="editBwrBookLoanModal"
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