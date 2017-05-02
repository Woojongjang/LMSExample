<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	String bookName = request.getParameter("bookName");
	String bookPublisher = request.getParameter("bookPublisher");
	String bookGenre = request.getParameter("bookGenre");
	String bookAuthor = request.getParameter("bookAuthor");
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
		<h4 class="modal-title">Update Book Details</h4>
	</div>
	<form action="editBook" id="editbookform" method="post">
		<div class="modal-body" class="container col-md-12">
			<p>Current details of the Book:</p>
			<ul>
				<li>Book Publisher: <p class="text-info"><%=bookPublisher%></p></li>
				<li>Book Authors: <p class="text-info"><%=bookAuthor%></p></li>
				<li>Book Genres: <p class="text-info"><%=bookGenre%></p></li>
			</ul>
			<p>Enter the details of the Book:</p>
			Book Name: <input type="text" class="form-control" name="bookName" id="bookName" value="<%=bookName%>" size="50"><br />
			Book ID: <input type="text" class="form-control" name="bookId" id="bookId" value="<%=bookId%>" readonly="readonly"><br/>
			<br/>
			<div>
				<label for="selauth">Select Author(s) of the Book (hold ctrl to select more than one):</label>
				<select multiple class="form-control" name="authorlist" form="editbookform" id="selauth" size="8">
				<%for(Author a: authorList) { %>
					<option value="<%=a.getAuthorId() %>"><%=a.getAuthorName() %></option>
				<%} %>
				</select>
				<label for="selpub">Select Publisher of the Book:</label>
				<select class="form-control" name="publisherlist" form="editbookform" id="selpub">
					<%for(Publisher p: publisherList) { %>
						<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName() %></option>
					<%} %>
				</select>
				<label for="selgenr">Select Genre(s) of the Book (hold ctrl to select more than one):</label>
				<select multiple class="form-control" name="genrelist" form="editbookform" id="selgenr" size="8">
					<%for(Genre g: genreList) { %>
						<option value="<%=g.getGenreId()%>"><%=g.getGenreName() %></option>
					<%} %>
				</select>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
</div>