package com.gcit.lms.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BookLoanService;

/**
 * Servlet implementation class BookLoanServlet
 */
@WebServlet({"/BookLoanServlet", "/addBookLoan", "/pageBookLoans", "/editBookLoan", "/deleteBookLoan", "/searchBookLoans"})
public class BookLoanServlet extends HttpServlet {
	private static final long serialVersionUID = -7537088797044049099L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public BookLoanServlet() {
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
		case "/pageBookLoans":
			pageBookLoans(request);
			forwardPath = "/viewbookloans.jsp";
			break;
		case "/searchBookLoans":
			String brData = searchBookLoans(request);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(brData);
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
		switch(reqUrl) {
			case "/addBookLoan":
				addBookLoan(request);
				forwardPath = "/viewbookloans.jsp";
				break;
			case "/editBookLoan":
				editBookLoan(request);
				forwardPath = "/viewbookloans.jsp";
				break;
			case "/deleteBookLoan":
				deleteBookLoan(request);
				forwardPath = "/viewbookloans.jsp";
				break;
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private void addBookLoan(HttpServletRequest request) {
		BookLoan loan = new BookLoan();
		HttpSession session = request.getSession();
		
		
		Borrower borrower = new Borrower();
		//borrower.setBorrowerName(request.getParameter("borrowerName"));
		if((Integer) session.getAttribute("userId") == 0){
			String[] selectedBorrowers = request.getParameterValues("borrowerlist");
			if(selectedBorrowers!=null && selectedBorrowers.length!=0) {
				borrower.setBorrowerId(Integer.parseInt(selectedBorrowers[0]));
			}
		}
		else {
			borrower.setBorrowerId(Integer.parseInt("borrowerId"));
		}
		loan.setBorrower(borrower);
		
		LibraryBranch branch = new LibraryBranch();
		//branch.setBranchName(request.getParameter("branchName");
		String[] selectedBranch = request.getParameterValues("branchlist");
		if(selectedBranch!=null && selectedBranch.length!=0) {
			branch.setBranchId(Integer.parseInt(selectedBranch[0]));
		}
		loan.setBranch(branch);
		
		Book book = new Book();
		//book.setTitle(request.getParameter("bookName"));
		String[] selectedBook = request.getParameterValues("booklist");
		if(selectedBook!=null && selectedBook.length!=0) {
			book.setBookId(Integer.parseInt(selectedBook[0]));
		}
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
	
	private void pageBookLoans(HttpServletRequest request) {
		Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
		BookLoanService service = new BookLoanService();
		try {
			request.setAttribute("loans", service.getAllBookLoans(pageNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editBookLoan(HttpServletRequest request) {
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
		String dateDue = (request.getParameter("dateDue"));
		String dateIn = (request.getParameter("dateIn"));
		
		if(dateOut.equals("NOT CHECKED OUT?")) {
			dateOut = null;
		}
		if(dateDue.equals("NO DUE DATE")) {
			dateDue = null;
		}
		if(dateIn.equals("NOT CHECKED IN")) {
			dateIn = null;
		}
		
		loan.setBook(book);
		loan.setBranch(branch);
		loan.setBorrower(borrower);
		loan.setDateChecked(dateOut);
		loan.setDateDue(dateDue);
		loan.setDateIn(dateIn);
		BookLoanService service = new BookLoanService();
		try {
			service.editBookLoan(loan);
			request.setAttribute("message", "Book Loan Edit Successful");
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
	
	private String searchBookLoans(HttpServletRequest request) {
		String searchString = request.getParameter("searchString");
		BookLoanService service = new BookLoanService();
		StringBuffer strBuf = new StringBuffer();
		Integer pageNo = 1;
		Integer loanSize = 1;
		try {
			// request.setAttribute("authors", service.getAuthorsByName(1,
			// searchString));
			//ADD LAST AUTHOR WITH COUNT AS ID IF THIS DOESNT WORK
			String pageNum = request.getParameter("pageNo");
			if(pageNum != null) {
				pageNo = Integer.parseInt(pageNum);
			}
			List<BookLoan> loans = service.getBookLoanByName(pageNo, searchString);
			loanSize = loans.size();
			request.setAttribute("count", loanSize);
			strBuf.append("<thead><tr><th>#</th><th>Borrower Name</th><th>Borrower ID</th><th>Branch Name</th>"
					+"<th>Book Name</th><th>Checked OUT</th><th>Due Date</th><th>Checked IN</th>"
					+ "<th>Edit</th><th>Delete</th></tr></thead><tbody>");
			for (BookLoan bl : loans) {
				String borrowerName = bl.getBorrower().getBorrowerName();
				Integer borrowerID = bl.getBorrower().getBorrowerId();
				String branchName = bl.getBranch().getBranchName();
				Integer branchID = bl.getBranch().getBranchId();
				String bookName = bl.getBook().getTitle();
				Integer bookID = bl.getBook().getBookId();
				String dateOut = bl.getDateChecked();
				String dateDue = bl.getDateDue();
				String dateIn = bl.getDateIn();
				if(dateDue==null) {
					dateDue = "NO DUE DATE";
				}
				if(dateIn==null) {
					dateIn = "NOT CHECKED IN";
				}
				if(dateOut==null) {
					dateOut = "NOT CHECKED OUT?";
				}
				String borrowerNameEnc = URLEncoder.encode(borrowerName, "UTF-8");
				String branchNameEnc = URLEncoder.encode(branchName, "UTF-8");
				String bookNameEnc = URLEncoder.encode(bookName, "UTF-8");
				String dateOutEnc = URLEncoder.encode(dateOut, "UTF-8");
				String dateDueEnc = URLEncoder.encode(dateDue, "UTF-8");
				String dateInEnc = URLEncoder.encode(dateIn, "UTF-8");
				strBuf.append("<tr><td>" + (loans.indexOf(bl) + 1) + "</td><td>" + borrowerName + "</td><td>"+borrowerID+"</td>"
						+"<td>"+branchName+"</td><td>"+bookName+"</td><td>"+dateOut+"</td><td>"+dateDue+"</td><td>"+dateIn+"</td>");
				strBuf.append("<td><a href='borrowerloan.jsp?borrowerId="+borrowerID+"' class='btn btn-info' style='color:white'>Loans</a></td>");
				strBuf.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBookLoanModal' "
				+"href='editbookloan.jsp?borrowerId="+borrowerID+"&amp;borrowerName="+borrowerNameEnc
				+"&amp;branchId="+branchID+"&amp;branchName="+branchNameEnc+"&amp;bookId=<%=bookID%>"
				+"&amp;bookName="+bookNameEnc+"&amp;dateOut="+dateOutEnc+"&amp;dateDue="+dateDueEnc+"&amp;dateIn="+dateInEnc+"'>Update</button></td>");
				strBuf.append("<td><form action='deleteBorrower' method='post'><input type='hidden' name='bookId' id='bookId'"
						+" value='"+bookID+"'><input type='hidden' name='borrowerId' id='borrowerId' value='"+borrowerID+"'>"
						+"<input type='hidden' name='borrowerId' id='borrowerId' value='"+borrowerID+"'>"
						+"<input type='hidden' name='branchId' id='branchId' value='"+branchID+"'>"
						+"<input type='hidden' name='dateOut' id='dateOut' value='"+dateOut+"'>"
						+"<button class='btn btn-danger'>Delete</button></form></td></tr>");
			}	
			strBuf.append("</tbody>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonData = "{ \"key1\": \""+strBuf.toString()+"\", \"key2\": "+loanSize+" }";
		return jsonData;
	}
}
