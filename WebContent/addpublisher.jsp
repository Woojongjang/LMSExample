<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Publisher Details</h4>
	</div>
	<form action="addPublisher" method="post">
		<div class="modal-body">
			<p>Enter the details of the new Publisher:</p>
			Publisher Name: <input type="text" class="form-control" name="publisherName" id="publisherName" value="New Publisher Name"><br />
			Publisher Address: <input type="text" class="form-control" name="publisherAddr" id="publisherAddr" value="New Publisher Address"><br />
			Publisher Phone: <input type="text" class="form-control" name="publisherPhone" id="publisherPhone" value="New Publisher Phone">
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>