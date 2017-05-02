package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.LibraryBranch;
/**
 * This is a DAO
 * @branch woojong
 *
 */
public class LibraryBranchDAO extends BaseDAO{
	
	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}

	public void addBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		save("insert into tbl_library_branch (branchName, branchAddress) values (?,?)",
				new Object[] {branch.getBranchName(),branch.getBranchAddress()});
	}
	
	public void updateBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[]{branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}
	
	public void deleteBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		save("delete from tbl_library_branch where branchId = ?", new Object[] {branch.getBranchId()});
	}
	
	public List<LibraryBranch> readAllBranches(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		return read("select * from tbl_library_branch", null);
	}
	
	public LibraryBranch readBranchByID(Integer branchID) throws ClassNotFoundException, SQLException{
		List<LibraryBranch> branches = read("select * from tbl_library_branch where branchId = ?", new Object[]{branchID});
		if(branches!=null && !branches.isEmpty()){
			return branches.get(0);
		}
		return null;
	}
	
	public List<LibraryBranch> readBranchesByName(String  branchName) throws ClassNotFoundException, SQLException{
		setPageSize(10);
		branchName = "%"+branchName+"%";
		return read("select * from tbl_library_branch where branchName like ?", new Object[]{branchName});
	}
	
	public List<LibraryBranch> readBranchesByName(Integer pageNo, String  branchName) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		branchName = "%"+branchName+"%";
		List<LibraryBranch> retList = read("select * from tbl_library_branch where branchName like ?", new Object[]{branchName});
		return retList;
	}
	
	public Integer getBranchesCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_library_branch", null);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		//BookDAO bdao = new BookDAO(conn);
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()){
			LibraryBranch p = new LibraryBranch();
			p.setBranchId(rs.getInt("branchId"));
			String name = rs.getString("branchName");
			String addr = rs.getString("branchAddress");
			if(name == null) {
				p.setBranchName("NO LIBRARY BRANCH NAME");
			}
			else if(name.equals("")){
				p.setBranchName("NO LIBRARY BRANCH NAME");
			}
			else {
				p.setBranchName(name);
			}
			if(addr == null) {
				p.setBranchAddress("NO LIBRARY BRANCH ADDRESS");
			}
			else if(addr.equals("")){
				p.setBranchAddress("NO LIBRARY BRANCH ADDRESS");
			}
			else {
				p.setBranchAddress(addr);
			}
			//a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_branches where branchId = ?)", new Object[]{a.getBranchId()}));
			branches.add(p);
		}
		return branches;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()){
			LibraryBranch p = new LibraryBranch();
			p.setBranchId(rs.getInt("branchId"));
			p.setBranchName(rs.getString("branchName"));
			p.setBranchAddress(rs.getString("branchAddress"));
			branches.add(p);
		}
		return branches;
	}

}
