<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Author Details</h4>
	</div>
	<form action="addAuthor" method="post">
		<div class="modal-body">
			<p>Enter the details of the Author:</p>
			Author Name: <input type="text" name="authorName" id="authorName" value="New Author Name"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>