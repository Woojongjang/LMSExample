package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {
	
	public LibraryBranch getBranchById(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readBranchByID(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBranchBookCount(LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.getBranchBookCount(branch);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void addBranchBookCount(Integer bookId, Integer branchId, Integer count) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.updateBranchBooks(bookId,branchId,count);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public List<Book> getBooksNotInBranch(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.getBooksNotInBranch(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void addNewBranchBook(Integer bookId, Integer branchId, Integer count) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.addBranchBooks(bookId,branchId,count);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public HashMap<Book,Integer> getLibraryBookSearch(Integer pageNoThenCount, String search, Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> branchList = pdao.readBranchesByName(pageNoThenCount, search);
			return null;
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
