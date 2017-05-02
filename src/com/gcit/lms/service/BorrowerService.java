package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BorrowerDAO;
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
}
