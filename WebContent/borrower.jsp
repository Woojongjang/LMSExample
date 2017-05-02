<%@include file="include.html"%>
<div class="container">
	<%
		if (request.getAttribute("message") != null) {
	%>
	<div class="alert alert-success" role="alert">
		<strong>SUCCESS</strong> ${message}
	</div>
	<%
		}
	%>
	<%
		if (request.getAttribute("error") != null) {
	%>
	<div class="alert alert-danger" role="alert">
		<strong>ERROR</strong> ${error}
	</div>
	<%
		}
	%>	
<div class='jumbotron'>
	<h1>Borrower Services</h1>
	<p>Welcome to GCIT Library Management System, User <%=session.getAttribute("userId") %></p>
</div>
<div class='row'>
	<div class="panel panel-default">
		<div class="panel-heading">
		<a href="choosebranch.jsp" style="text-decoration: none">
			<h3 class="panel-title">Borrow A Book</h3>
			</a>
		</div>
		<div class="panel-body">Add Library Branches, Delete Library Branches, Update
			Library Branch Information, List All Library Branches</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
		<a href="borrowerloan.jsp" style='text-decoration: none'>
			<h3 class="panel-title" style='color:white'>View Your Book Loans</h3>
			</a>
		</div>
		<div class="panel-body">Add Borrowers, Delete Borrowers, Update
			Borrower Information, List All Borrowers, Over-ride Due Date for Book Loans</div>
	</div>
</div>
</div>