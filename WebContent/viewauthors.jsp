<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	AdminService service = new AdminService();
	Integer authorsCount = service.getAuthorsCount();
	Integer numOfPages = 0;
	if (authorsCount % 10 > 0) {
		numOfPages = authorsCount / 10 + 1;
	} else {
		numOfPages = authorsCount / 10;
	}
	List<Author> authors = new ArrayList<>();
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = service.getAllAuthors(1);
	}
	System.out.println(request.getRequestURI());
	System.out.println(request.getRequestURL());
	System.out.println(request.getContextPath());
	System.out.println(request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length()));
%>
<script>
	function searchAuthors(pageNum){
		$.ajax({
			url: "searchAuthors",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		})
		.done(function (data){
			//$('#paginateId').//try data[] for string array
			//alert('hello');
			$("#paginateId").empty();
			$("#paginateId").html(data.key2);
			//var returnedData = JSON.parse(data);
			//alert(returnedData.key2);
			$('#authorsTable').html(data.key1);
			//alert('done');
		})
		.fail(function(data) {
		    alert('IT FAILED');
		});
	}
//	function refreshData(){
//	    $.post("url", {data},function(data) {
//	            if(paginationPagesTotal >1){
//	                showPagination(paginationPagesTotal);
//	                $("#paginateBox").show();
//	            }else
//	                $("#paginateBox").hide();
//	        });
//	}
</script>
<div class="container" role="main">
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
		<h1>List of Authors in LMS</h1>
	</div>
	<button type="button" class="open-AuthorEdit btn btn-success"
		data-toggle="modal" data-target="#editAuthorModal"
		href="addauthor.jsp">Add New Author</button><br/><br/>
	<div class="input-group">
		<form action="searchAuthors">
			<input type="text" class="form-control" name="searchString"
				id="searchString" placeholder="Search for..."
				oninput="searchAuthors(1);">
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
			<li><a href="pageAuthors?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchAuthors(<%=pageNo-1 %>);"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
		<%} %>
			<%
				for (int i = 1; i <= numOfPages; i++) {
			%>
					<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
				//Integer nextPage = Integer.parseInt(request.getParameter("pageNo"));
				//String toPage = "";
				//if(nextPage < numOfPages) {
				//	toPage = request.getParameter("pageNo")+1;
				//}
			%>
			<%
			if(pageNo != numOfPages) {%>
			<li><a href="pageAuthors?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
			<%} %>
		</ul>
	</nav>
	<div class="col-md-6">
		<table class="table table-striped" id="authorsTable">
			<thead>
				<tr>
					<th>#</th>
					<th>Author Name</th>
					<th>Author ID</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Author a : authors) {
						String authName = a.getAuthorName();
						Integer authId = a.getAuthorId();
						String authNameFirst = URLEncoder.encode(authName,"UTF-8");
				%>
				<tr>
					<td><%=authors.indexOf(a) + 1%></td>
					<td><%=authName%></td>
					<td><%=authId%></td>
					<td><button data-id="<%=authId%>" data-name="<%=authName%>" type="button" class="open-AuthorEdit btn btn-primary"
							data-toggle="modal" data-target="#editAuthorModal"
							href="editauthor.jsp?authorId=<%=authId%>&amp;authorName=<%=authNameFirst%>">Update</button></td>
					<td><form action="deleteAuthor" method="post">
							<input type="hidden" name="authorId" id="authorId" value="<%=authId%>">
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
<div class="modal fade" id="editAuthorModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">....</div>
	</div>
</div>

<script>
$(document).ready(function() {
	$('.modal').on('hidden.bs.modal', function(e)
		    { 
		        $(this).removeData();
		    }) ;
	//e.preventDefault();
	//var _self = $(this);
	//var aName = _self.data('name');
	//var aId = _self.data('id');
	//alert([aName,aId]);
	//$("#authorName").val(aName);
	//$("#authorId").val(aId);
	//$(_self.attr('href')).modal('show');
});
</script>