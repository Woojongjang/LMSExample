<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Library Branch Details</h4>
	</div>
	<form action="addBranch" method="post">
		<div class="modal-body">
			<p>Enter the details of the new Branch:</p>
			Branch Name: <input type="text" name="branchName" id="branchName" value="New Branch Name"><br />
			Branch Address: <input type="text" name="branchAddr" id="branchAddr" value="New Branch Address"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>