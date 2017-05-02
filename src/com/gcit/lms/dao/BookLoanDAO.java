package com.gcit.lms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class BookLoanDAO extends BaseDAO{
	
	public BookLoanDAO(Connection conn) {
		super(conn);
	}

	public void addBookLoan(BookLoan book) throws ClassNotFoundException, SQLException{
		System.out.println("BOOK: "+book.getBook().getTitle());
		System.out.println("USER: "+book.getBorrower().getBorrowerId());
		System.out.println("LIB: "+book.getBranch().getBranchName());
		System.out.println("DUE: "+book.getDateChecked());
		save("insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate,dateIn) values (?,?,?,?,?,?)",
				new Object[] {book.getBook().getBookId(),book.getBranch().getBranchId(),
						book.getBorrower().getBorrowerId(),book.getDateChecked(),book.getDateDue(),book.getDateIn()});
	}
	
	public void addBookLoanAutoDue(BookLoan loan) throws ClassNotFoundException, SQLException{
		CallableStatement cStmt = conn.prepareCall("{call insert_book_loans(?, ?, ?, ?)}");
		System.out.println("AUTO INCREMENT");
		System.out.println("BOOK: "+loan.getBook().getBookId());
		System.out.println("USER: "+loan.getBorrower().getBorrowerId());
		System.out.println("LIB: "+loan.getBranch().getBranchId());
		System.out.println("DUE: "+loan.getDateChecked());
		
	    cStmt.setInt(1, loan.getBook().getBookId());
	    cStmt.setInt(2, loan.getBranch().getBranchId());
	    cStmt.setInt(3, loan.getBorrower().getBorrowerId());
	    cStmt.setString(4, loan.getDateChecked());
	    cStmt.executeQuery();
	}
	
	public void addBookLoanGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_genres values (?, ?)", new Object[] {genreId, bookId});
	}
	
	public void addBookLoanPublisher(Integer bookId, Integer pubId) throws ClassNotFoundException, SQLException{
		save("update tbl_book set pubId = ? where bookId = ?", new Object[] {pubId, bookId});
	}
	
//	public void deleteBookLoanAuthors(BookLoan book) throws ClassNotFoundException, SQLException{
//		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?;", 
//				new Object[] {book.getBook().getBookId(), book.getBranch().getBranchId(),
//						book.getBorrower().getBorrowerId(), book.getDateChecked()});
//	}
	
	public void deleteBookLoanGenres(Integer bookId) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_genres where bookId = ?", new Object[] {bookId});
	}
	
	public void updateBookLoan(BookLoan book) throws ClassNotFoundException, SQLException{
		Book bookId = book.getBook();
		LibraryBranch branchId = book.getBranch();
		Borrower borrowId = book.getBorrower();
		String dateChecked = book.getDateChecked();
		
		String dateDue = book.getDateDue();
		String dateIn = book.getDateIn();
		save("update tbl_book_loans set dueDate = ?, set dateIn = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[]{dateDue, dateIn, bookId, branchId, borrowId, dateChecked});
	}
	
	public void deleteBookLoan(BookLoan book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] {book.getBook().getBookId(), book.getBranch().getBranchId(),
						book.getBorrower().getBorrowerId(), book.getDateChecked()});
	}
	
	public List<BookLoan> readBookLoanByID(Integer pageNo, Borrower borrower) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageSize(10);
			setPageNo(pageNo);
		}
		List<BookLoan> books = read("select * from tbl_book_loans where cardNo = ?", new Object[]{borrower.getBorrowerId()});
		return books;
	}
	
	public List<BookLoan> readBookLoansByString(Integer pageNo, String search) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageSize(10);
			setPageNo(pageNo);
		}
		search = "%"+search+"%";
		List<BookLoan> retList = read("select * from tbl_book_loans where (bookId like ? or branchId like ? or cardNo like ? or dateOut like ? or dueDate like ? or dateIn like ?)"
				+" or bookId IN (select bookId from tbl_book where title like ?) or branchId in (select branchId from tbl_library_branch where branchName like ?)"
				+" or cardNo In (select cardNo from tbl_borrower where name like ?)",
				new Object[]{search, search, search, search, search, search, search, search, search});
		return retList;
	}
	
	public List<BookLoan> readAllBookLoans(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		return read("select * from tbl_book_loans", null);
	}

	public Integer getBookLoansCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_book_loans", null);
	}
	
	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookLoan> books = new ArrayList<>();
		BookDAO bkdao = new BookDAO(conn);
		BorrowerDAO bwdao = new BorrowerDAO(conn);
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		while(rs.next()){
			//System.out.println("GOING INSIDE rs.NEXZT FOR BOOK LOAN");
			Book book = bkdao.readBookByID(rs.getInt("bookId"));
			LibraryBranch branch = lbdao.readBranchByID(rs.getInt("branchId"));
			Borrower borrower = bwdao.readBorrowerByID(rs.getInt("cardNo"));
			String dateChecked = rs.getString("dateOut");
			
			String dateDue = rs.getString("dueDate");
			String dateIn = rs.getString("dateIn");
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBook(book);
			bookLoan.setBranch(branch);
			bookLoan.setBorrower(borrower);
			bookLoan.setDateChecked(dateChecked);
			if(dateDue != null) {
				bookLoan.setDateDue(dateDue);
			}
			if(dateIn!=null) {
				bookLoan.setDateIn(dateIn);
			}
			//System.out.println("Book Name:"+bookLoan.getBook().getTitle());
			books.add(bookLoan);
		}
		return books;
	}
	
	@Override
	public List<BookLoan> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookLoan> books = new ArrayList<>();
		BookDAO bkdao = new BookDAO(conn);
		BorrowerDAO bwdao = new BorrowerDAO(conn);
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		while(rs.next()){
			BookLoan b = new BookLoan();
			Book book = bkdao.readBookByID(rs.getInt("bookId"));
			LibraryBranch branch = lbdao.readBranchByID(rs.getInt("branchId"));
			Borrower borrower = bwdao.readBorrowerByID(rs.getInt("cardNo"));
			String dateChecked = rs.getString("dateOut");
			
			String dateDue = rs.getString("dueDate");
			String dateIn = rs.getString("dateIn");
			b.setBook(book);
			b.setBranch(branch);
			b.setBorrower(borrower);
			b.setDateChecked(dateChecked);
			if(dateDue != null) {
				b.setDateDue(dateDue);
			}
			if(dateIn!=null) {
				b.setDateIn(dateIn);
			}
			books.add(b);
		}
		return books;
	}
}
