<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	AdminService service = new AdminService();
	Integer publishersCount = service.getPublishersCount();
	Integer numOfPages = 0;
	if (publishersCount % 10 > 0) {
		numOfPages = publishersCount / 10 + 1;
	} else {
		numOfPages = publishersCount / 10;
	}
	List<Publisher> publishers = new ArrayList<>();
	if (request.getAttribute("publishers") != null) {
		publishers = (List<Publisher>) request.getAttribute("publishers");
	} else {
		publishers = service.getAllPublishers(1);
	}
%>
<script>
	function searchPublishers(pageNum){
		$.ajax({
			url: "searchPublishers",
			dataType: "text json",
			data:{
				searchString: $('#searchString').val(),
				pageNo: pageNum
			}
		}).done(function (data){
			$("#paginateId").empty();
			$('#publishersTable').html(data.key1)
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
			<h1>List of Publishers in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editPublisherModal"
			href="addpublisher.jsp">Add New Publisher</button><br/><br/>
		<div class="input-group">
			<form action="searchPublishers">
				<input type="text" class="form-control" name="searchString"
					id="searchString" placeholder="Search for..."
					oninput="searchPublishers(1);">
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
				<li><a href="pagePublishers?pageNo=<%=pageNo-1 %>" aria-label="Previous" onclick="searchPublishers(<%=pageNo-1 %>);"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
			<%} %>
				<%
					for (int i = 1; i <= numOfPages; i++) {
				%>
						<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<%
				if(pageNo != numOfPages) {%>
				<li><a href="pagePublishers?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<%} %>
			</ul>
		</nav>
		<div class="col-md-12">
			<table class="table table-striped" id="publishersTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Publisher Name</th>
						<th>Publisher ID</th>
						<th>Publisher Address</th>
						<th>Publisher Phone</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Publisher p : publishers) {
							String publisherName = p.getPublisherName();
							String publisherAddr = p.getPublisherAddress();
							String publisherPhone = p.getPublisherPhone();
							Integer publisherID = p.getPublisherId();
							String publisherNameEnc = URLEncoder.encode(publisherName, "UTF-8");
							String publisherAddrEnc = URLEncoder.encode(publisherAddr, "UTF-8");
							String publisherPhoneEnc = URLEncoder.encode(publisherPhone, "UTF-8");
							
					%>
					<tr>
						<td><%=publishers.indexOf(p) + 1%></td>
						<td><%=publisherName%></td>
						<td><%=publisherID%></td>
						<td><%=publisherAddr%></td>
						<td><%=publisherPhone%></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editPublisherModal"
								href="editpublisher.jsp?publisherId=<%=publisherID%>&amp;publisherName=<%=publisherNameEnc%>&amp;publisherAddr=<%=publisherAddrEnc%>&amp;publisherPhone=<%=publisherPhoneEnc%>">Update</button></td>
						<td><form action="deletePublisher" method="post">
							<input type="hidden" name="publisherId" id="publisherId" value="<%=publisherID%>">
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
<div class="modal fade" id="editPublisherModal"
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