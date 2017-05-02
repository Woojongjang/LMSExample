<%@page import="java.util.Map"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	Integer userId = -3; //neither admin nor borrower if = -3. 0 is admin.
	userId = (Integer) session.getAttribute("userId");
	Integer borrowerId = 0;
	try{
		if(userId == null) {
			userId = -3; //user is error: no one?
		}
		if(userId == 0) { //user is admin
			borrowerId = 0;
		}
		else { //user is someone else
			if(userId > 0) {
				borrowerId = userId; //user is borrower
			}
			else {
				borrowerId = -1; // user is probably a librarian (likely not)
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	LibrarianService service = new LibrarianService();
	HashMap<Book,Integer> bookCount = new HashMap<>();
	LibraryBranch branch = new LibraryBranch();
	Integer booksNum = 0;
	Integer branchId = 0;
	if (request.getAttribute("chosenBranch") != null) {
		branchId = (Integer)request.getAttribute("chosenBranch");
		//System.out.println("chosen Branch: "+branchId);
	}
	else {
		System.out.println("chosenBranch IS NULL branchId => 1");
		branchId = 1;
	}
	branch = service.getBranchById(branchId);
	bookCount = branch.getBooksCount();
	String branchName = branch.getBranchName();
	String branchAddr = branch.getBranchAddress();
	Integer branchesCount = service.getBranchBookCount(branch);
	Integer numOfPages = 0;
	if (branchesCount % 10 > 0) {
		numOfPages = branchesCount / 10 + 1;
	} else {
		numOfPages = branchesCount / 10;
	}
%>
<script>
	function searchBranches(pageNum){
		$.ajax({
			url: "searchBranches",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#branchesTable').html(data.key1)
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
			<h1>List of Books in <%=branchName %>, <%=branchAddr %>, ID: <%=branchId %></h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#borrowBookModal"
			href="addbranchbook.jsp?branchId=<%=branchId%>">Add New Book To Branch</button><br/><br/>
		<div class="input-group">
			<form action="searchBranches">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchBranches(1);">
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
				<li><a href="pageBranches?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchBranches(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageBranches?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageBranches?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div class="col-md-12">
			<label for="branchesTable">Available Books:</label>
			<table class="table table-striped" id="branchesTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Book Title</th>
						<th>Book ID</th>
						<th>Book Count</th>
						<th>Borrow Book</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Map.Entry<Book, Integer> bc : bookCount.entrySet()) {
							Book book = bc.getKey();
							Integer count = bc.getValue();
							String bookName = book.getTitle();
							Integer bookId = book.getBookId();
							
							String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
							String branchAddrEnc = URLEncoder.encode(branchAddr, "UTF-8");
							String bookNameEnc = URLEncoder.encode(bookName, "UTF-8");
					%>
					<tr>
						<td><%=booksNum+=1%></td>
						<td><%=bookName%></td>
						<td><%=bookId%></td>
						<td><%=count%></td>
						<%if(borrowerId > 0) {
							if(count > 0) {%>
						<td><form action="borrowBook" method="post">
							<input type="hidden" name="branchId" id="branchId" value="<%=branchId%>">
							<input type="hidden" name="bookId" id="bookId" value="<%=bookId%>">
							<input type="hidden" name="borrowerId" id="borrowerId" value="<%=borrowerId%>">
							<button  class="btn btn-primary">Borrow</button>
							</form></td>
						<%}else {%>
							<td style='color:threedlightshadow;'>BOOKS ALL CHECKED OUT</td>
						<%}
							} 
						  else {%>
						  <td><button type="button" class="btn btn-info"
								data-toggle="modal" data-target="#borrowBookModal"
								href="updatebranchbook.jsp?bookId=<%=bookId%>&amp;bookName=<%=bookNameEnc%>
								&amp;branchId=<%=branchId%>&amp;branchName=<%=branchNameEnc%>
								&amp;bookCount=<%=count%>">Update Book Count</button></td>
								
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
<div class="modal fade" id="borrowBookModal"
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