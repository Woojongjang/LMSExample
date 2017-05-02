<%@include file="include.html"%>
<%session.setAttribute("userId", -1);
//SHOW LIBRARY BRANCH HERE WHERE YOU CAN ADD BOOKS
%>
<div class="container">
<div class='jumbotron'>
	<h1>Librarian Services</h1>
</div>
<div class='row'>
	<div class="panel panel-default">
		<div class="panel-heading">
		<a href="viewbranches.jsp" style="text-decoration: none">
			<h3 class="panel-title">Library Branch Services</h3>
			</a>
		</div>
		<div class="panel-body">Add Library Branches, Delete Library Branches, Update
			Library Branch Information, List All Library Branches</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
		<a href="adminbookloan.jsp" style='text-decoration: none'>
			<h3 class="panel-title" style='color:white'>Return A Book</h3>
			</a>
		</div>
		<div class="panel-body">Add Borrowers, Delete Borrowers, Update
			Borrower Information, List All Borrowers, Over-ride Due Date for Book Loans</div>
	</div>
</div>
</div>