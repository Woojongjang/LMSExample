package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet({"/LibrarianServlet", "/librarianLogin", "/libChooseBranch", "/addBookBranch", "/addNewBookBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 4860309050334960765L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServlet() {
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
		case "/librarianLogin":
			//System.out.println("inside LibrarianServlet:" +request.getRequestURI());
			forwardPath = "/librarianlogin.jsp";
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
		case "/libChooseBranch":
			chooseBranch(request);
			forwardPath = "/viewlibrarybooks.jsp";
			break;
		case "/addBookBranch":
			addBookBranch(request);
			forwardPath = "/viewlibrarybooks.jsp";
			break;
		case "/addNewBookBranch":
			addNewBookBranch(request);
			forwardPath = "/viewlibrarybooks.jsp";
		default:
			break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	private void chooseBranch(HttpServletRequest request) {
		Integer chosenBranch;
		String[] selectedBranches = request.getParameterValues("liblist");
		if(selectedBranches!=null && selectedBranches.length!=0) {
			chosenBranch = Integer.parseInt(selectedBranches[0]);
			request.setAttribute("chosenBranch", chosenBranch);
		}
		else {
			System.out.println("the selction is fethcing null?");
		}
	}
	
	private void addBookBranch(HttpServletRequest request) {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		Book book = new Book();
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		Integer count = Integer.parseInt(request.getParameter("count"));
		Integer newCount = Integer.parseInt(request.getParameter("newCount"));
		Integer updateCount = count + newCount;
		LibrarianService service = new LibrarianService();
		try {
			service.addBranchBookCount(book.getBookId(),branch.getBranchId(),updateCount);
			request.setAttribute("chosenBranch", branch.getBranchId());
			request.setAttribute("addMsg", "Library Branch Book Updated Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addNewBookBranch(HttpServletRequest request) {
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		Integer bookId=null;
		
		String[] selectedBooks = request.getParameterValues("booklist");
		if(selectedBooks!=null && selectedBooks.length!=0) {
			bookId = Integer.parseInt(selectedBooks[0]);
		}
		
		Integer count = Integer.parseInt(request.getParameter("count"));
		
		LibrarianService service = new LibrarianService();
		try {
			service.addNewBranchBook(bookId,branch.getBranchId(),count);
			request.setAttribute("chosenBranch", branch.getBranchId());
			request.setAttribute("addMsg", "Library Branch New Book Added Successfuly");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
