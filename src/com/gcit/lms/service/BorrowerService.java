package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;

public class BorrowerService {

	public boolean checkBorrowerId(Integer borrowerID) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			Borrower borrower = brdao.readBorrowerByID(borrowerID);
			if(borrower == null) {
				return false;
			}
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return false;
	}
	
	public Borrower getBorrowerById(Integer borrowerID) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			Borrower borrower = brdao.readBorrowerByID(borrowerID);
			return borrower;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void returnBookLoan(BookLoan loan) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.returnBookLoan(loan);
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
}
