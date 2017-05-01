<%@include file="include.html"%>
<%
System.out.println(request.getRequestURI());
%>
<div class='container' role='main'>
	<div class='page-header'>
		<h1>Borrower Log-In</h1>
	</div>
	<div class="col-sm-4">
		<form action="borrowLogin" method='get'>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">Card Number:</span>
			  <input type="text" class="form-control" name="cardId" id="cardId" placeholder="Type your card number here..." aria-describedby="basic-addon1">
			</div><br/>
			<input type="submit" value="Login">
		</form>
	</div>
</div>