<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	AdminService service = new AdminService();
	Integer branchesCount = service.getBranchesCount();
	Integer numOfPages = 0;
	if (branchesCount % 10 > 0) {
		numOfPages = branchesCount / 10 + 1;
	} else {
		numOfPages = branchesCount / 10;
	}
	List<LibraryBranch> branches = new ArrayList<>();
	if (request.getAttribute("branches") != null) {
		branches = (List<LibraryBranch>) request.getAttribute("branches");
	} else {
		branches = service.getAllBranches(1);
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
			<h1>List of Library Branches in LMS</h1>
		</div>
		<button type="button" class="btn btn-success"
			data-toggle="modal" data-target="#editBranchModal"
			href="addbranch.jsp">Add New Branch</button><br/><br/>
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
			<table class="table table-striped" id="branchesTable">
				<thead>
					<tr>
						<th>#</th>
						<th>Branch Name</th>
						<th>Branch ID</th>
						<th>Branch Address</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (LibraryBranch p : branches) {
							String branchName = p.getBranchName();
							String branchAddr = p.getBranchAddress();
							Integer branchID = p.getBranchId();
							String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
							String branchAddrEnc = URLEncoder.encode(branchAddr, "UTF-8");
							
					%>
					<tr>
						<td><%=branches.indexOf(p) + 1%></td>
						<td><%=branchName%></td>
						<td><%=branchID%></td>
						<td><%=branchAddr%></td>
						<td><button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#editBranchModal"
								href="editbranch.jsp?branchId=<%=branchID%>&amp;branchName=<%=branchNameEnc%>&amp;branchAddr=<%=branchAddrEnc%>">Update</button></td>
						<td><form action="deleteBranch" method="post">
							<input type="hidden" name="branchId" id="branchId" value="<%=branchID%>">
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
<div class="modal fade" id="editBranchModal"
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