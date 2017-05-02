<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Borrower Details</h4>
	</div>
	<form action="addBorrower" method="post">
		<div class="modal-body">
			<p>Enter the details of the new Borrower:</p>
			Borrower Name: <input type="text" class="form-control" name="borrowerName" id="borrowerName" value="New Borrower Name"><br />
			Borrower Address: <input type="text" class="form-control" name="borrowerAddr" id="borrowerAddr" value="New Borrower Address"><br />
			Borrower Phone: <input type="text" class="form-control" name="borrowerPhone" id="borrowerPhone" value="New Borrower Phone">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>