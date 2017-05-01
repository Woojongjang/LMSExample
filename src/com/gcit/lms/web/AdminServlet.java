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
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor", "/editAuthor", "/pageAuthors", "/deleteAuthor", "/searchAuthors",
	"/pageBooks", "/editBook", "/addBook", "/deleteBook", "/searchBooks", 
	"/addGenre", "/pageGenres", "/editGenre", "/deleteGenre", "/searchGenres"})
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
		Integer pageNo = 1;
		Integer authorSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("count");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<Author> authors = service.getAuthorsByName(pageNo, searchString);
			authorSize = authors.size();
			request.setAttribute("pageNo", authorSize);
			strBuf.append("<thead><tr><th>#</th><th>Author Name</th><th>Author ID</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Author a : authors) {
				strBuf.append("<tr><td>" + authors.indexOf(a) + 1 + "</td><td>" + a.getAuthorName() + "</td><td>"+a.getAuthorId()+"</td>");
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
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+authorSize+" }";
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
			request.setAttribute("pageNo", bookSize);
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
				strBuf.append("<tr><td>" + books.indexOf(b) + 1 + "</td><td>" + bookName + "</td><td>"+b.getBookId()+"</td><td>"+bookAuthors+"</td><td>"+bookGenres+"</td><td>"+bookPublisher+"</td>");
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
			request.setAttribute("pageNo", genreSize);
			strBuf.append("<thead><tr><th>#</th><th>Genre Name</th><th>Genre ID</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (Genre g : genres) {
				String genreName = g.getGenreName();
				Integer genreID = g.getGenreId();
				String genreNameEnc = URLEncoder.encode(genreName, "UTF-8");
				strBuf.append("<tr><td>" + genres.indexOf(g) + 1 + "</td><td>" + genreName + "</td><td>"+genreID+"</td>");
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
}
