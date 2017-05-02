<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	List<Publisher> publisherList = new ArrayList<>();
	List<Author> authorList = new ArrayList<>();
	List<Genre> genreList = new ArrayList<>();
	publisherList = service.getAllPublishers(null);
	authorList = service.getAllAuthors(null);
	genreList = service.getAllGenres(null);
	//Author author = service.getAuthorByPk(authorId);
	//System.out.println("NAAAAME: "+authorName);
	//System.out.println("IDDDD: "+authorId);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Add New Book Details</h4>
	</div>
	<form action="addBook" id="addbookform" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Enter the details of the new Book:</p>
			Book Name: <input type="text" class="form-control" name="bookName" id="bookName" value="Add New Book Name..." size="50"><br />
		</div>
		<br/>
		<div>	
			<label for="selauth">Select Author(s) of the Book (hold ctrl to select more than one):</label>
			<select class="form-control" name="authorlist" form="addbookform" multiple="multiple" id="selauth" size="8">
			<%for(Author a: authorList) { %>
				<option value="<%=a.getAuthorId() %>"><%=a.getAuthorName() %></option>
			<%} %>
			</select>
			<label for="selpub">Select Publisher of the Book:</label>
			<select class="form-control" name="publisherlist" form="addbookform" id="selpub">
				<%for(Publisher p: publisherList) { %>
					<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName() %></option>
				<%} %>
			</select>
			<label for="selgenr">Select Genre(s) of the Book (hold ctrl to select more than one):</label>
			<select class="form-control" name="genrelist" form="addbookform" multiple="multiple" id="selgenr" size="8">
				<%for(Genre g: genreList) { %>
					<option value="<%=g.getGenreId()%>"><%=g.getGenreName() %></option>
				<%} %>
			</select>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>