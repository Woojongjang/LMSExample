package com.gcit.lms.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor", "/editAuthor", "/pageAuthors", "/deleteAuthor", "/searchAuthors",
	"/pageBooks", "/editBook", "/addBook", "/deleteBook", "/searchBooks", 
	"/addGenre", "/pageGenres", "/editGenre", "/deleteGenre", "/searchGenres",
	"/addPublisher", "/pagePublishers", "/editPublisher", "/deletePublisher", "/searchPublishers",
	"/addBorrower", "/pageBorrowers", "/editBorrower", "/deleteBorrower", "/searchBorrowers",
	"/addBranch", "/pageBranches", "/editBranch", "/deleteBranch", "/searchBranches"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "/welcome.jsp";
		Boolean isAjax = Boolean.FALSE;
		switch (reqUrl) {
		case "/pageAuthors":
			pageAuthors(request);
			forwardPath = "/viewauthors.jsp";
			break;
		case "/pageBooks":
			pageBooks(request);
			forwardPath = "/viewbooks.jsp";
			break;
		case "/pageGenres":
			pageGenres(request);
			forwardPath = "/viewgenres.jsp";
			break;
		case "/pagePublishers":
			pagePublishers(request);
			forwardPath = "/viewpublishers.jsp";
			break;
		case "/pageBranches":
			pageBranches(request);
			forwardPath = "/viewlibraries.jsp";
			break;
		case "/pageBorrowers":
			pageBorrowers(request);
			forwardPath = "/viewborrowers.jsp";
			break;
		case "/searchAuthors":
			String aData = searchAuthors(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(aData);
		    //System.out.println(aData);
			// forwardPath = "/viewauthors.jsp";
			isAjax = Boolean.TRUE;
			break;
		case "/searchBooks":
			String bData = searchBooks(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(bData);
		    //System.out.println(bData);
		    isAjax = Boolean.TRUE;
		    break;
		case "/searchGenres":
			String gData = searchGenres(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(gData);
			isAjax = Boolean.TRUE;
			break;
		case "/searchPublishers":
			String pData = searchPublishers(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(pData);
			isAjax = Boolean.TRUE;
			break;
		case "/searchBorrowers":
			String brData = searchBorrowers(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(brData);
			isAjax = Boolean.TRUE;
			break;
		case "/searchBranches":
			String lbData = searchBranches(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(lbData);
			isAjax = Boolean.TRUE;
			break;
		default:
			break;
		}
		if (!isAjax) {
			RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "/welcome.jsp";
		switch (reqUrl) {
		case "/addAuthor":
			addAuthor(request);
			forwardPath = "/viewauthors.jsp";
			break;
		case "/editAuthor":
			editAuthor(request);
			forwardPath = "/viewauthors.jsp";
			break;
		case "/deleteAuthor":
			deleteAuthor(request);
			forwardPath = "/viewauthors.jsp";
			break;
		case "/editBook":
			editBook(request);
			forwardPath = "/viewbooks.jsp";
			break;
		case "/addBook":
			addBook(request);
			forwardPath = "/viewbooks.jsp";
			break;
		case "/deleteBook":
			deleteBook(request);
			forwardPath = "/viewbooks.jsp";
			break;
		case "/addGenre":
			addGenre(request);
			forwardPath = "/viewgenres.jsp";
			break;
		case "/editGenre":
			editGenre(request);
			forwardPath = "/viewgenres.jsp";
			break;
		case "/deleteGenre":
			deleteGenre(request);
			forwardPath = "/viewgenres.jsp";
		case "/addPublisher":
			addPublisher(request);
			forwardPath = "/viewpublishers.jsp";
			break;
		case "/editPublisher":
			editPublisher(request);
			forwardPath = "/viewpublishers.jsp";
			break;
		case "/deletePublisher":
			deletePublisher(request);
			forwardPath = "/viewpublishers.jsp";
			break;
		case "/addBorrower":
			addBorrower(request);
			forwardPath = "/viewborrowers.jsp";
			break;
		case "/editBorrower":
			editBorrower(request);
			forwardPath = "/viewborrowers.jsp";
			break;
		case "/deleteBorrower":
			deleteBorrower(request);
			forwardPath = "/viewborrowers.jsp";
			break;
		case "/addBranch":
			addBranch(request);
			forwardPath = "/viewlibraries.jsp";
			break;
		case "/editBranch":
			editBranch(request);
			forwardPath = "/viewlibraries.jsp";
			break;
		case "/deleteBranch":
			deleteBranch(request);
			forwardPath = "/viewlibraries.jsp";
			break;
		default:
			break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}


	/**
	 * @param request
	 */
	private void deleteAuthor(HttpServletRequest request) {
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
		AdminService service = new AdminService();
		try {
			service.deleteAuthor(author);
			request.setAttribute("deleteMsg", "Author Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addAuthor(HttpServletRequest request) {
		Author author = new Author();
		author.setAuthorName(request.getParameter("authorName"));
		AdminService service = new AdminService();
		try {
			service.addAuthor(author);
			request.setAttribute("addMsg", "Author Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editAuthor(HttpServletRequest request) {
		Author author = new Author();
		author.setAuthorName(request.getParameter("authorName"));
		author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
		AdminService service = new AdminService();
		try {
			service.editAuthor(author);
			request.setAttribute("message", "Author Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pageAuthors(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("authors", service.getAllAuthors(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchAuthors(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		StringBuffer strPagBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer authorSize = 1;
		Integer numOfPages = 0;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("pageNo");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Author> authors = service.getAuthorsByName(pageNo, searchString);
			authorSize = service.getAuthorSearchCount(searchString);
			if (authorSize % 10 > 0) {
				numOfPages = authorSize / 10 + 1;
			} else {
				numOfPages = authorSize / 10;
			}
			request.setAttribute("pageNo", authorSize);
			strBuf.append("<thead><tr><th>#</th><th>Author Name</th><th>Author ID</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Author a : authors) {
				strBuf.append("<tr><td>" + (authors.indexOf(a) + 1) + "</td><td>" + a.getAuthorName() + "</td><td>"+a.getAuthorId()+"</td>");
				strBuf.append(
						"<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editAuthorModal'href='editauthor.jsp?authorId="
								+ a.getAuthorId() + "'>Update</button></td>");
				strBuf.append("<td><button type='button' class='btn btn-danger' href='deleteAuthor?authorId="
						+ a.getAuthorId() + "'>Delete</button></td></tr>");
			}
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		String pageString = request.getParameter("pageNo");
//		Integer pageNo = 1;
//		if(pageString != null) {
//			pageNo = Integer.parseInt(request.getParameter("pageNo"));
//		}
//		if(pageNo != 1) {
//	%>
//		<li><a href='pageAuthors?pageNo=<%=pageNo-1 %>' aria-label='Previous' onclick='searchAuthors(<%=pageNo-1 %>);'> <span aria-hidden='true'>&laquo;</span></a></li>
//	<%} %>
//		<%
//			for (int i = 1; i <= numOfPages; i++) {
//		%>
//				<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
//		<%
//			}
//			//Integer nextPage = Integer.parseInt(request.getParameter("pageNo"));
//			//String toPage = "";
//			//if(nextPage < numOfPages) {
//			//	toPage = request.getParameter("pageNo")+1;
//			//}
//		%>
//		<%
//		if(pageNo != numOfPages) {%>
//		<li><a href="pageAuthors?pageNo=<%=pageNo+1 %>" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
//		</a></li>
//		<%} %>
		if(pageNo!=1) {
			strPagBuf.append("<li><a href='#' aria-label='Previous' onclick='searchAuthors("+(pageNo-1)+");'> <span aria-hidden='true'>&laquo;</span></a></li>");
		}
		for(int i = 1; i <= numOfPages; i++) {
			strPagBuf.append("<li><a href='#' onclick='searchAuthors("+i+");'>"+i+"</a></li>");
		}
		if(numOfPages!=pageNo){
			strPagBuf.append("<li><a href='#' aria-label='Next'  onclick='searchAuthors("+(pageNo+1)+");'> <span aria-hidden='true'>&raquo;</span></a></li>");
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": \""+strPagBuf.toString()+"\" }";
		return jsonData;
	}
	
	private void pageBooks(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("books", service.getAllBooks(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param request
	 */
	private void editBook(HttpServletRequest request) {
		Book book = new Book();
		Publisher publish = new Publisher();
		List<Author> bookAuthors = new ArrayList<>();
		List<Genre> bookGenres = new ArrayList<>();
		book.setTitle(request.getParameter("bookName"));
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		String[] pubIdString = request.getParameterValues("publisherlist");
		if(pubIdString!=null && pubIdString.length!=0) {
			publish.setPublisherId(Integer.parseInt(pubIdString[0]));
		}
		book.setPublisher(publish);
		String[] selectedAuthors = request.getParameterValues("authorlist");
		String[] selectedGenres = request.getParameterValues("genrelist");
		if(selectedAuthors!=null && selectedAuthors.length!=0) {
			for(String aString : selectedAuthors) {
				Author aName = new Author();
				aName.setAuthorId(Integer.parseInt(aString));
				bookAuthors.add(aName);
			}
		}
		book.setAuthors(bookAuthors);
		if(selectedGenres!=null && selectedGenres.length!=0) {
			for(String gString : selectedGenres) {
				Genre gName = new Genre();
				gName.setGenreId(Integer.parseInt(gString));
				bookGenres.add(gName);
			}
		}
		book.setGenres(bookGenres);
		AdminService service = new AdminService();
		try {
			service.editBook(book);
			request.setAttribute("message", "Book Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addBook(HttpServletRequest request) {
		Book book = new Book();
		Publisher publish = new Publisher();
		List<Author> bookAuthors = new ArrayList<>();
		List<Genre> bookGenres = new ArrayList<>();
		book.setTitle(request.getParameter("bookName"));
		AdminService service = new AdminService();
		
		String[] pubIdString = request.getParameterValues("publisherlist");
		if(pubIdString!=null && pubIdString.length!=0) {
			publish.setPublisherId(Integer.parseInt(pubIdString[0]));
		}
		book.setPublisher(publish);
		String[] selectedAuthors = request.getParameterValues("authorlist");
		String[] selectedGenres = request.getParameterValues("genrelist");
		if(selectedAuthors!=null && selectedAuthors.length!=0) {
			for(String aString : selectedAuthors) {
				Author aName = new Author();
				aName.setAuthorId(Integer.parseInt(aString));
				bookAuthors.add(aName);
			}
		}
		book.setAuthors(bookAuthors);
		if(selectedGenres!=null && selectedGenres.length!=0) {
			for(String gString : selectedGenres) {
				Genre gName = new Genre();
				gName.setGenreId(Integer.parseInt(gString));
				bookGenres.add(gName);
			}
		}
		book.setGenres(bookGenres);
		try {
			service.addBook(book);
			request.setAttribute("addMsg", "Book Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * @param request
	 */
	private void deleteBook(HttpServletRequest request) {
		Book book = new Book();
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		AdminService service = new AdminService();
		try {
			service.deleteBook(book);
			request.setAttribute("deleteMsg", "Author Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchBooks(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer bookSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("count");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Book> books = service.getBooksByName(pageNo, searchString);
			bookSize = books.size();
			request.setAttribute("count", bookSize);
			strBuf.append("<thead><tr><th>#</th><th>Book Name</th><th>Book ID</th><th>Authors</th><th>Genres</th><th>Publisher</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Book b : books) {
				String bookName = b.getTitle();
				String bookNameSpace = URLEncoder.encode(bookName,"UTF-8");
				String bookPublisher = b.getPublisher().getPublisherName();
				String bookPubSpace =URLEncoder.encode(bookPublisher,"UTF-8");
				String bookGenres = b.getGenreString();
				String bookAuthors = b.getAuthorString();
				String bookGenreSpace = URLEncoder.encode(bookGenres,"UTF-8");
				String bookAuthorSpace = URLEncoder.encode(bookAuthors,"UTF-8");
				strBuf.append("<tr><td>" + (books.indexOf(b) + 1) + "</td><td>" + bookName + "</td><td>"+b.getBookId()+"</td><td>"+bookAuthors+"</td><td>"+bookGenres+"</td><td>"+bookPublisher+"</td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBookModal'href='editbook.jsp?bookId="
						+ b.getBookId()+"&amp;bookName="+bookNameSpace+"&amp;bookPublisher="+bookPubSpace
						+"&amp;bookGenre="+bookGenreSpace+"&amp;bookAuthor="+bookAuthorSpace+"'>Update</button></td>");
				strBuf.append("<td><form action='deleteBook' method='post'><input type='hidden' name='bookId' id='bookId' value='"
						+b.getBookId()+"'><button class='btn btn-danger'>Delete</button></form></td></tr>");
			}
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+bookSize+" }";
		return jsonData;
	}
	
	private void addGenre(HttpServletRequest request) {
		Genre genre = new Genre();
		genre.setGenreName(request.getParameter("genreName"));
		AdminService service = new AdminService();
		try {
			service.addGenre(genre);
			request.setAttribute("addMsg", "Genre Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pageGenres(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("genres", service.getAllGenres(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editGenre(HttpServletRequest request) {
		Genre genre = new Genre();
		genre.setGenreName(request.getParameter("genreName"));
		genre.setGenreId(Integer.parseInt(request.getParameter("genreId")));
		AdminService service = new AdminService();
		try {
			service.editGenre(genre);
			request.setAttribute("message", "Genre Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteGenre(HttpServletRequest request) {
		Genre genre = new Genre();
		genre.setGenreId(Integer.parseInt(request.getParameter("genreId")));
		AdminService service = new AdminService();
		try {
			service.deleteGenre(genre);
			request.setAttribute("deleteMsg", "Genre Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchGenres(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer genreSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("count");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Genre> genres = service.getGenresByName(pageNo, searchString);
			genreSize = genres.size();
			request.setAttribute("count", genreSize);
			strBuf.append("<thead><tr><th>#</th><th>Genre Name</th><th>Genre ID</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Genre g : genres) {
				String genreName = g.getGenreName();
				Integer genreID = g.getGenreId();
				String genreNameEnc = URLEncoder.encode(genreName, "UTF-8");
				strBuf.append("<tr><td>" + (genres.indexOf(g) + 1) + "</td><td>" + genreName + "</td><td>"+genreID+"</td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editGenreModal' "
				+"href='editgenre.jsp?genreId="+genreID+"&amp;genreName="+genreNameEnc+"'>Update</button></td>");
				strBuf.append("<td><form action='deleteGenre' method='post'><input type='hidden' name='genreId' id='genreId' value='"
				+genreID+"'><button class='btn btn-danger'>Delete</button></form></td></tr>");
			}
//			<tr>
//			<td><%=genres.indexOf(g) + 1%></td>
//			<td><%=genreName%></td>
//			<td><%=genreID%></td>
//			<td><button type="button" class="btn btn-primary"
//					data-toggle="modal" data-target="#editGenreModal"
//					href="editgenre.jsp?genreId=<%=genreID%>&amp;genreName=<%=genreNameEnc%>">Update</button></td>
//			<td><form action="deleteGenre" method="post">
//				<input type="hidden" name="genreId" id="genreId" value="<%=genreID%>">
//				<button  class="btn btn-danger">Delete</button>
//				</form></td>
//				</tr>
//				<%
//					}
//				%>
//			</tbody>
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+genreSize+" }";
		return jsonData;
	}
	
	private void addPublisher(HttpServletRequest request) {
		Publisher publisher = new Publisher();
		publisher.setPublisherName(request.getParameter("publisherName"));
		publisher.setPublisherAddress(request.getParameter("publisherAddr"));
		publisher.setPublisherPhone(request.getParameter("publisherPhone"));
		AdminService service = new AdminService();
		try {
			service.addPublisher(publisher);
			request.setAttribute("addMsg", "Publisher Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pagePublishers(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("publishers", service.getAllPublishers(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editPublisher(HttpServletRequest request) {
		Publisher publisher = new Publisher();
		publisher.setPublisherName(request.getParameter("publisherName"));
		publisher.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));
		publisher.setPublisherAddress(request.getParameter("publisherAddr"));
		publisher.setPublisherPhone(request.getParameter("publisherPhone"));
		AdminService service = new AdminService();
		try {
			service.editPublisher(publisher);
			request.setAttribute("message", "Publisher Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deletePublisher(HttpServletRequest request) {
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));
		AdminService service = new AdminService();
		try {
			service.deletePublisher(publisher);
			request.setAttribute("deleteMsg", "Publisher Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchPublishers(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer publisherSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("pageNo");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Publisher> publishers = service.getPublishersByName(pageNo, searchString);
			publisherSize = publishers.size();
			request.setAttribute("count", publisherSize);
			strBuf.append("<thead><tr><th>#</th><th>Publisher Name</th><th>Publisher ID</th>"
					+"<th>Publisher Address</th><th>Publisher Phone</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Publisher p : publishers) {
				String publisherName = p.getPublisherName();
				String publisherAddr = p.getPublisherAddress();
				String publisherPhone = p.getPublisherPhone();
				Integer publisherID = p.getPublisherId();
				String publisherNameEnc = URLEncoder.encode(publisherName, "UTF-8");
				String publisherAddrEnc = URLEncoder.encode(publisherAddr, "UTF-8");
				String publisherPhoneEnc = URLEncoder.encode(publisherPhone, "UTF-8");
				strBuf.append("<tr><td>" + (publishers.indexOf(p) + 1) + "</td><td>" + publisherName + "</td><td>"+publisherID+"</td>"
						+"<td>"+publisherAddr+"</td><td>"+publisherPhone+"</td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editPublisherModal' "
				+"href='editpublisher.jsp?publisherId="+publisherID+"&amp;publisherName="+publisherNameEnc+
				"&amp;publisherAddr="+publisherAddrEnc+"&amp;publisherPhone="+publisherPhoneEnc+"'>Update</button></td>");
				strBuf.append("<td><form action='deletePublisher' method='post'><input type='hidden' name='publisherId' id='publisherId'"
						+" value='"+publisherID+"'><button class='btn btn-danger'>Delete</button></form></td></tr>");
			}
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+publisherSize+" }";
		return jsonData;
	}
	
	private void addBorrower(HttpServletRequest request) {
		Borrower borrower = new Borrower();
		borrower.setBorrowerName(request.getParameter("borrowerName"));
		borrower.setBorrowerAddress(request.getParameter("borrowerAddr"));
		borrower.setBorrowerPhone(request.getParameter("borrowerPhone"));
		AdminService service = new AdminService();
		try {
			service.addBorrower(borrower);
			request.setAttribute("addMsg", "Borrower Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pageBorrowers(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("borrowers", service.getAllBorrowers(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editBorrower(HttpServletRequest request) {
		Borrower borrower = new Borrower();
		borrower.setBorrowerName(request.getParameter("borrowerName"));
		borrower.setBorrowerId(Integer.parseInt(request.getParameter("borrowerId")));
		borrower.setBorrowerAddress(request.getParameter("borrowerAddr"));
		borrower.setBorrowerPhone(request.getParameter("borrowerPhone"));
		AdminService service = new AdminService();
		try {
			service.editBorrower(borrower);
			request.setAttribute("message", "Borrower Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteBorrower(HttpServletRequest request) {
		Borrower borrower = new Borrower();
		borrower.setBorrowerId(Integer.parseInt(request.getParameter("borrowerId")));
		AdminService service = new AdminService();
		try {
			service.deleteBorrower(borrower);
			request.setAttribute("deleteMsg", "Borrower Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchBorrowers(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer borrowerSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("pageNo");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Borrower> borrowers = service.getBorrowersByName(pageNo, searchString);
			borrowerSize = borrowers.size();
			request.setAttribute("count", borrowerSize);
			strBuf.append("<thead><tr><th>#</th><th>Borrower Name</th><th>Borrower Card Number</th>"
					+"<th>Borrower Address</th><th>Borrower Phone</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Borrower p : borrowers) {
				String borrowerName = p.getBorrowerName();
				String borrowerAddr = p.getBorrowerAddress();
				String borrowerPhone = p.getBorrowerPhone();
				Integer borrowerID = p.getBorrowerId();
				String borrowerNameEnc = URLEncoder.encode(borrowerName, "UTF-8");
				String borrowerAddrEnc = URLEncoder.encode(borrowerAddr, "UTF-8");
				String borrowerPhoneEnc = URLEncoder.encode(borrowerPhone, "UTF-8");
				strBuf.append("<tr><td>" + (borrowers.indexOf(p) + 1) + "</td><td>" + borrowerName + "</td><td>"+borrowerID+"</td>"
						+"<td>"+borrowerAddr+"</td><td>"+borrowerPhone+"</td>");
				strBuf.append("<td><a href='borrowerloan.jsp?borrowerId="+borrowerID+"' class='btn btn-info' style='color:white'>Loans</a></td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBorrowerModal' "
				+"href='editborrower.jsp?borrowerId="+borrowerID+"&amp;borrowerName="+borrowerNameEnc+
				"&amp;borrowerAddr="+borrowerAddrEnc+"&amp;borrowerPhone="+borrowerPhoneEnc+"'>Update</button></td>");
				strBuf.append("<td><form action='deleteBorrower' method='post'><input type='hidden' name='borrowerId' id='borrowerId'"
						+" value='"+borrowerID+"'><button class='btn btn-danger'>Delete</button></form></td></tr>");
			}
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+borrowerSize+" }";
		return jsonData;
	}
	
	private void addBranch(HttpServletRequest request) {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(request.getParameter("branchName"));
		branch.setBranchAddress(request.getParameter("branchAddr"));
		AdminService service = new AdminService();
		try {
			service.addBranch(branch);
			request.setAttribute("addMsg", "Library Branch Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pageBranches(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		AdminService service = new AdminService();
		try {
			request.setAttribute("branches", service.getAllBranches(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editBranch(HttpServletRequest request) {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(request.getParameter("branchName"));
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		branch.setBranchAddress(request.getParameter("branchAddr"));
		AdminService service = new AdminService();
		try {
			service.editBranch(branch);
			request.setAttribute("message", "Library Branch Edit Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteBranch(HttpServletRequest request) {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		AdminService service = new AdminService();
		try {
			service.deleteBranch(branch);
			request.setAttribute("deleteMsg", "LibraryBranch Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchBranches(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		AdminService service = new AdminService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer branchSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("pageNo");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<LibraryBranch> branches = service.getBranchesByName(pageNo, searchString);
			branchSize = service.getBranchSearchCount(searchString);
			Integer numOfPages = 0;
			if (branchSize % 10 > 0) {
				numOfPages = branchSize / 10 + 1;
			} else {
				numOfPages = branchSize / 10;
			}
			request.setAttribute("count", branchSize);
			strBuf.append("<thead><tr><th>#</th><th>Branch Name</th><th>Branch Id</th>"
					+"<th>Branch Address</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (LibraryBranch p : branches) {
				String branchName = p.getBranchName();
				String branchAddr = p.getBranchAddress();
				Integer branchID = p.getBranchId();
				String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
				String branchAddrEnc = URLEncoder.encode(branchAddr, "UTF-8");
				strBuf.append("<tr><td>" + (branches.indexOf(p) + 1) + "</td><td>" + branchName + "</td><td>"+branchID+"</td>"
						+"<td>"+branchAddr+"</td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBranchModal' "
				+"href='editbranch.jsp?branchId="+branchID+"&amp;branchName="+branchNameEnc+
				"&amp;branchAddr="+branchAddrEnc+"'>Update</button></td>");
				strBuf.append("<td><form action='deleteBranch' method='post'><input type='hidden' name='branchId' id='branchId'"
						+" value='"+branchID+"'><button class='btn btn-danger'>Delete</button></form></td></tr>");
			}
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+branchSize+" }";
		return jsonData;
	}
}
