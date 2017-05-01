<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Genre Details</h4>
	</div>
	<form action="addGenre" method="post">
		<div class="modal-body">
			<p>Enter the details of the new Genre:</p>
			Genre Name: <input type="text" name="genreName" id="genreName" value="New Genre Name"><br />
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>