package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;

public class BookLoanService {
	
	public Integer getBookLoansCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			return bldao.getBookLoansCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void addBookLoan(BookLoan loan) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.addBookLoanAutoDue(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void editBookLoan(BookLoan loan) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.updateBookLoan(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public List<BookLoan> getAllBookLoans(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			return bldao.readAllBookLoans(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookLoan> getUserBookLoans(Integer pageNo, Borrower borrower) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			return bldao.readBookLoanByID(pageNo, borrower);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void deleteBookLoan(BookLoan loan) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.deleteBookLoan(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}

	/*
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<BookLoan> getBookLoanByName(Integer pageNoThenCount, String search) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			List<BookLoan> loanList = bldao.readBookLoansByString(pageNoThenCount, search);
			return loanList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
}
