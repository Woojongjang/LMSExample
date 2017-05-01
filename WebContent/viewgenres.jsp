<%@page import="javax.print.URIException"%>
<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	AdminService service = new AdminService();
	Integer genresCount = service.getGenresCount();
	Integer numOfPages = 0;
	if (genresCount % 10 > 0) {
		numOfPages = genresCount / 10 + 1;
	} else {
		numOfPages = genresCount / 10;
	}
	List<Genre> genres = new ArrayList<>();
	if (request.getAttribute("genres") != null) {
		genres = (List<Genre>) request.getAttribute("genres");
	} else {
		genres = service.getAllGenres(1);
	}
%>
<script>
	function searchGenres(pageNum){
		$.ajax({
			url: "searchGenres",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				count: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#genreTable').html(data.key1)
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
			<h1>List of Genres in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editGenreModal"
			href="addgenre.jsp">Add New Genre</button><br/><br/>
		<div class="input-group">
			<form action="searchBooks">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchGenres(1);">
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
				<li><a href="pageGenres?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchBooks(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pageGenres?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pageGenres?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div class="col-md-8">
			<table class="table table-striped" id="genreTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Genre Name</th>
						<th>Genre ID</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Genre g : genres) {
							String genreName = g.getGenreName();
							Integer genreID = g.getGenreId();
							String genreNameEnc = URLEncoder.encode(genreName, "UTF-8");
					%>
					<tr>
						<td><%=genres.indexOf(g) + 1%></td>
						<td><%=genreName%></td>
						<td><%=genreID%></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editGenreModal"
								href="editgenre.jsp?genreId=<%=genreID%>&amp;genreName=<%=genreNameEnc%>">Update</button></td>
						<td><form action="deleteGenre" method="post">
							<input type="hidden" name="genreId" id="genreId" value="<%=genreID%>">
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
<div class="modal fade" id="editGenreModal"
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