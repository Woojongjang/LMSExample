package com.gcit.lms.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.BookLoanService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/BorrowerServlet", "/borrowLogin", "/bookLoanReturn", "/pageBorrowerLoans", "/chooseBranch", "/borrowBook",
				"/searchLibraryBooks"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 2888013528754996495L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
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
		case "/borrowLogin":
			//System.out.println("inside BorrowerServlet:" +request.getRequestURI());
			forwardPath = borrowerLogin(request); 
			break;
		case "/pageBorrowerLoans":
			pageBorrowerLoans(request);
			forwardPath = "/borrowerloan.jsp";
			break;
		case "/searchLibraryBooks":
//			String lbData = searchLibraryBooks(request);
//			response.setContentType("application/json");
//		    response.setCharacterEncoding("UTF-8");
//		    response.getWriter().write(lbData);
//		    //System.out.println(aData);
//			// forwardPath = "/viewauthors.jsp";
//			isAjax = Boolean.TRUE;
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
		case "/bookLoanReturn":
			System.out.println("Returning a Book!");
			bookLoanReturn(request);
			forwardPath = "/borrowerloan.jsp";
			break;
		case "/chooseBranch":
			chooseBranch(request);
			forwardPath = "/viewlibrarybooks.jsp";
			break;
		case "/borrowBook":
			borrowBook(request);
			forwardPath = "/viewlibrarybooks.jsp";
			break;
		default:
			break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	private String borrowerLogin(HttpServletRequest request) {
		Integer cardNo = Integer.parseInt(request.getParameter("cardId"));
		BorrowerService service = new BorrowerService();
		String forwardPath = "/borrowerlogin.jsp";
		boolean loginCheck = false;
		
		try {
			loginCheck = service.checkBorrowerId(cardNo);
			HttpSession session = request.getSession();
			if(loginCheck) {
				//System.out.println("inside BorrowerServlet:SUCESS");
				session.setAttribute("userId", Integer.parseInt(request.getParameter("cardId")));
				forwardPath = "/borrower.jsp";
				request.setAttribute("message", "Login Successful");
				return forwardPath;
			}
			else {
				request.setAttribute("error", "Login UNSUCCESSFUL");
				return forwardPath;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forwardPath;
	}
	
	private void borrowBook(HttpServletRequest request) {
		BookLoan loan = new BookLoan();
		
		Borrower borrower = new Borrower();
		//borrower.setBorrowerName(request.getParameter("borrowerName"));
		borrower.setBorrowerId(Integer.parseInt(request.getParameter("borrowerId")));
		loan.setBorrower(borrower);
		
		LibraryBranch branch = new LibraryBranch();
		//branch.setBranchName(request.getParameter("branchName");
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		loan.setBranch(branch);
		
		Book book = new Book();
		//book.setTitle(request.getParameter("bookName"));
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		loan.setBook(book);
		
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		//Date newDueDateFormatted = ft.parse("thedateINPUTTED");
		//String newDate = ft.format(newDueDateFormatted);
		Date dateNow = new Date();
		String now = ft.format(dateNow);
		loan.setDateChecked(now);
		
		//loan.setDateDue(dateDue);
		//loan.setDateIn(dateIn);
		BookLoanService service = new BookLoanService();
		try {
			service.addBookLoan(loan);
			request.setAttribute("addMsg", "Book Loan Add Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pageBorrowerLoans(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		BookLoanService service = new BookLoanService();
		Integer borrowerId = Integer.parseInt(request.getParameter("borrowerId"));
		Borrower borrower = new Borrower();
		borrower.setBorrowerId(borrowerId);
		try {
			request.setAttribute("borrowerloans", service.getUserBookLoans(pageNo, borrower));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void bookLoanReturn(HttpServletRequest request) {
		BookLoan loan = new BookLoan();
		//String borrowerName = (request.getParameter("borrowerName"));
		Borrower borrower = new Borrower();
		Integer borrowerId = (Integer.parseInt(request.getParameter("borrowerId")));
		borrower.setBorrowerId(borrowerId);
		
		LibraryBranch branch = new LibraryBranch();
		//String branchName = (request.getParameter("branchName"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		branch.setBranchId(branchId);
		
		Book book = new Book();
		//String bookName = (request.getParameter("bookName"));
		Integer bookId = (Integer.parseInt(request.getParameter("bookId")));
		book.setBookId(bookId);
		
		String dateOut = (request.getParameter("dateOut"));
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date dateNow = new Date();
		String now = ft.format(dateNow);
		String dateIn = (now);
		
		loan.setBook(book);
		loan.setBranch(branch);
		loan.setBorrower(borrower);
		loan.setDateChecked(dateOut);
		
		loan.setDateIn(dateIn);
		BorrowerService service = new BorrowerService();
		try {
			service.returnBookLoan(loan);
			request.setAttribute("message", "Book Loan Return Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteBookLoan(HttpServletRequest request) {
		BookLoan loan = new BookLoan();
		Borrower borrower = new Borrower();
		//borrower.setBorrowerName(request.getParameter("borrowerName"));
		borrower.setBorrowerId(Integer.parseInt(request.getParameter("borrowerId")));
		loan.setBorrower(borrower);
		
		LibraryBranch branch = new LibraryBranch();
		//branch.setBranchName(request.getParameter("branchName");
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		loan.setBranch(branch);
		
		Book book = new Book();
		//book.setTitle(request.getParameter("bookName"));
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		loan.setBook(book);
		
		String dateOut = request.getParameter("dateOut");
		loan.setDateChecked(dateOut);
		
		BookLoanService service = new BookLoanService();
		try {
//			String dateOut = request.getParameter("dateOut");
//			System.out.println(dateOut);
//			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//			Date dateFormatted = ft.parse(dateOut);
//			dateOut = ft.format(dateFormatted);
//			loan.setDateChecked(dateOut);
			
			service.deleteBookLoan(loan);
			request.setAttribute("deleteMsg", "Book Loan Delete Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String searchLibraryBooks(HttpServletRequest request) {
//		String searchString = request.getParameter("searchString");
//		LibrarianService service = new LibrarianService();
//		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		StringBuffer strBuf = new StringBuffer();
//		LibraryBranch branch = new LibraryBranch();
//		Integer pageNo = 1;
		Integer loanSize = 1;
//		HashMap<Book,Integer> bookCount = new HashMap<>();
//		try {
//			//branch = service.getBranchById(branchId);
//			//bookCount = branch.getBooksCount();
//			// request.setAttribute("authors", service.getAuthorsByName(1,
//			// searchString));
//			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
//			String pageNum = request.getParameter("pageNo");
//			if(pageNum != null) {
//				pageNo = Integer.parseInt(pageNum);
//			}
//			bookCount = service.getLibraryBookSearch(pageNo, searchString, branchId);
//			loanSize = loans.size();
//			request.setAttribute("count", loanSize);
//			strBuf.append("<thead><tr><th>#</th><th>Borrower Name</th><th>Borrower ID</th><th>Branch Name</th>"
//					+"<th>Book Name</th><th>Checked OUT</th><th>Due Date</th><th>Checked IN</th>"
//					+ "<th>Edit</th><th>Delete</th></tr></thead><tbody>");
//			for (Map.Entry<Book, Integer> bc : bookCount.entrySet()) {
//				Book book = bc.getKey();
//				Integer count = bc.getValue();
//				String bookName = book.getTitle();
//				Integer bookId = book.getBookId();
//				
//				String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
//				String branchAddrEnc = URLEncoder.encode(branchAddr, "UTF-8");
//				String bookNameEnc = URLEncoder.encode(bookName, "UTF-8");
//				
//				<th>#</th>
//				<th>Book Title</th>
//				<th>Book ID</th>
//				<th>Book Count</th>
//				<th>Borrow Book</th>
//				strBuf.append("<tr><td>" + (loans.indexOf(bl) + 1) + "</td><td>" + borrowerName + "</td><td>"+borrowerID+"</td>"
//						+"<td>"+branchName+"</td><td>"+bookName+"</td><td>"+dateOut+"</td><td>"+dateDue+"</td><td>"+dateIn+"</td>");
//				strBuf.append("<td><a href='borrowerloan.jsp?borrowerId="+borrowerID+"' class='btn btn-info' style='color:white'>Loans</a></td>");
//				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBookLoanModal' "
//				+"href='editbookloan.jsp?borrowerId="+borrowerID+"&amp;borrowerName="+borrowerNameEnc
//				+"&amp;branchId="+branchID+"&amp;branchName="+branchNameEnc+"&amp;bookId=<%=bookID%>"
//				+"&amp;bookName="+bookNameEnc+"&amp;dateOut="+dateOutEnc+"&amp;dateDue="+dateDueEnc+"&amp;dateIn="+dateInEnc+"'>Update</button></td>");
//				strBuf.append("<td><form action='deleteBorrower' method='post'><input type='hidden' name='bookId' id='bookId'"
//						+" value='"+bookID+"'><input type='hidden' name='borrowerId' id='borrowerId' value='"+borrowerID+"'>"
//						+"<input type='hidden' name='borrowerId' id='borrowerId' value='"+borrowerID+"'>"
//						+"<input type='hidden' name='branchId' id='branchId' value='"+branchID+"'>"
//						+"<input type='hidden' name='dateOut' id='dateOut' value='"+dateOut+"'>"
//						+"<button class='btn btn-danger'>Delete</button></form></td></tr>");
//			}	
//			strBuf.append("</tbody>");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+loanSize+" }";
		return jsonData;
	}
	
	private void chooseBranch(HttpServletRequest request) {
		Integer chosenBranch;
		String[] selectedBranches = request.getParameterValues("liblist");
		if(selectedBranches!=null && selectedBranches.length!=0) {
			chosenBranch = Integer.parseInt(selectedBranches[0]);
			request.setAttribute("chosenBranch", chosenBranch);
		}
	}
}
