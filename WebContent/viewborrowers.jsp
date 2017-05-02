<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	AdminService service = new AdminService();
	Integer borrowersCount = service.getBorrowersCount();
	Integer numOfPages = 0;
	if (borrowersCount % 10 > 0) {
		numOfPages = borrowersCount / 10 + 1;
	} else {
		numOfPages = borrowersCount / 10;
	}
	List<Borrower> borrowers = new ArrayList<>();
	if (request.getAttribute("borrowers") != null) {
		borrowers = (List<Borrower>) request.getAttribute("borrowers");
	} else {
		borrowers = service.getAllBorrowers(1);
	}
%>
<script>
	function searchBorrowers(pageNum){
		$.ajax({
			url: "searchBorrowers",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#borrowersTable').html(data.key1)
		})
		.fail(function(data) {
		    alert('IT FAILED');
		});
	}
</script>

<div class="container" role="main">
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
			<h1>List of Borrowers in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editBorrowerModal"
			href="addborrower.jsp">Add New Borrower</button><br/><br/>
		<div class="input-group">
			<form action="searchBorrower">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchBorrowers(1);">
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
				<li><a href="pageBorrowers?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchBorrowers(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageBorrowers?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageBorrowers?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div class="col-md-12">
			<table class="table table-striped" id="borrowersTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Borrower Name</th>
						<th>Borrower Card Number</th>
						<th>Borrower Address</th>
						<th>Borrower Phone</th>
						<th>Book Loans</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Borrower p : borrowers) {
							String borrowerName = p.getBorrowerName();
							String borrowerAddr = p.getBorrowerAddress();
							String borrowerPhone = p.getBorrowerPhone();
							Integer borrowerID = p.getBorrowerId();
							String borrowerNameEnc = URLEncoder.encode(borrowerName, "UTF-8");
							String borrowerAddrEnc = URLEncoder.encode(borrowerAddr, "UTF-8");
							String borrowerPhoneEnc = URLEncoder.encode(borrowerPhone, "UTF-8");
							
					%>
					<tr>
						<td><%=borrowers.indexOf(p) + 1%></td>
						<td><%=borrowerName%></td>
						<td><%=borrowerID%></td>
						<td><%=borrowerAddr%></td>
						<td><%=borrowerPhone%></td>
						<td><a href='borrowerloan.jsp?borrowerId=<%=borrowerID%>' class='btn btn-info' style='color:white'>Loans</a></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editBorrowerModal"
								href="editborrower.jsp?borrowerId=<%=borrowerID%>&amp;borrowerName=<%=borrowerNameEnc%>&amp;borrowerAddr=<%=borrowerAddrEnc%>&amp;borrowerPhone=<%=borrowerPhoneEnc%>">Update</button></td>
						<td><form action="deleteBorrower" method="post">
							<input type="hidden" name="borrowerId" id="borrowerId" value="<%=borrowerID%>">
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
<div class="modal fade" id="editBorrowerModal"
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