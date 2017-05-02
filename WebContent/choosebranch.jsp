<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
	branches = service.getAllBranches(null);
%>
<div class='container' role='main'>
	<div class='page-header'>
		<h1>Choose a Library</h1>
	</div>
	<div class="col-sm-4">
		<form action="chooseBranch" id="choosebranchform" method="post">
			<label for="selectlib">Select A Library:</label>
			<select class="form-control" name="liblist" form="choosebranchform" id="selectlib">
			<%for(LibraryBranch lb: branches) { %>
				<option value="<%=lb.getBranchId()%>"><%=lb.getBranchId()%>) <%=lb.getBranchName() %>, <%=lb.getBranchAddress() %></option>
			<%} %>
			</select>
			<br/>
			<button type="submit" class="btn btn-primary">Go To Branch</button>
		</form>
	</div>
</div>