<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	
	AdminService service = new AdminService();
	Integer booksCount = service.getBooksCount();
	Integer numOfPages = 0;
	if (booksCount % 10 > 0) {
		numOfPages = booksCount / 10 + 1;
	} else {
		numOfPages = booksCount / 10;
	}
	List<Book> books = new ArrayList<>();
	if (request.getAttribute("books") != null) {
		books = (List<Book>) request.getAttribute("books");
	} else {
		books = service.getAllBooks(1);
	}
%>
<script>
	function searchBooks(pageNum){
		$.ajax({
			url: "searchBooks",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#booksTable').html(data.key1)
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
			<h1>List of Books in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editBookModal"
			href="addbook.jsp">Add New Book</button><br/><br/>
		<div class="input-group">
			<form action="searchBooks">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchBooks(1);">
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
				<li><a href="pageBooks?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchBooks(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageBooks?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageBooks?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div class="col-md-15">
			<table class="table table-striped" id="booksTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Book Name</th>
						<th>Book ID</th>
						<th>Authors</th>
						<th>Genres</th>
						<th>Publisher</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Book b : books) {
							String bookName = b.getTitle();
							String bookNameSpace = URLEncoder.encode(bookName,"UTF-8");
							String bookPublisher = b.getPublisher().getPublisherName();
							String bookPubSpace =URLEncoder.encode(bookPublisher,"UTF-8");
							String bookGenres = b.getGenreString();
							String bookAuthors = b.getAuthorString();
							String bookGenreSpace = URLEncoder.encode(bookGenres,"UTF-8");
							String bookAuthorSpace = URLEncoder.encode(bookAuthors,"UTF-8");
					%>
					<tr>
						<td><%=books.indexOf(b) + 1%></td>
						<td><%=bookName%></td>
						<td><%=b.getBookId()%></td>
						<td><%=bookAuthors%></td>
						<td><%=bookGenres%></td>
						<td><%=bookPublisher%></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editBookModal"
								href="editbook.jsp?bookId=<%=b.getBookId()%>&amp;bookName=<%=bookNameSpace%>&amp;bookPublisher=<%=bookPubSpace%>&amp;bookGenre=<%=bookGenreSpace%>&amp;bookAuthor=<%=bookAuthorSpace%>">Update</button></td>
						<td><form action="deleteBook" method="post">
							<input type="hidden" name="bookId" id="bookId" value="<%=b.getBookId()%>">
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
<div class="modal fade" id="editBookModal"
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