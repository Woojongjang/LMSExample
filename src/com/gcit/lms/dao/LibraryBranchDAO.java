package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gcit.lms.entity.Book;
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
	
	public List<Book> getBooksNotInBranch(Integer  branchId) throws ClassNotFoundException, SQLException{
		BookDAO bdao = new BookDAO(conn);
		return bdao.read("select * from tbl_book where bookId not in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId});
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
	
	public Integer getBranchBookCount(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_book_copies where branchId = ?"
				, new Object[]{branch.getBranchId()});
	}
	
	public List<Book> readBranchBooksById(Integer pageNo, String search, Integer branchId) throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		bdao.setPageNo(pageNo);
		bdao.setPageSize(10);
//		select distinct b.bookId, b.title, b.pubId, bc.noOfCopies from tbl_book b inner join tbl_book_copies bc on bc.bookId = b.bookId
//		where b.bookId like ? and b.title like ? and b.pubId like ? and b.bookId in 
//		(select bookId from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ?));
		books = bdao.read("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId});
		return books;
	}

	public void updateBranchBooks(Integer bookId, Integer branchId, Integer count) throws ClassNotFoundException, SQLException{
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[]{count, bookId, branchId});
	}
	
	public void addBranchBooks(Integer bookId, Integer branchId, Integer count) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?,?,?)",
				new Object[]{bookId, branchId, count});
	}
	
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		BookDAO bdao = new BookDAO(conn);
		List<LibraryBranch> branches = new ArrayList<>();
		HashMap<Book, Integer> booksCount = new HashMap<>();
		Integer count;
		Book book;
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
			//p.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_branches where branchId = ?)", new Object[]{a.getBranchId()}));
			List<Book> books = bdao.readFirstLevel("select * from tbl_book tb inner join tbl_book_copies bc on tb.bookId = bc.bookId where bc.branchId = ?", new Object[]{p.getBranchId()});
			for(Book b : books) {
				count = readCount("select noOfCopies as COUNT from tbl_book_copies where branchId = ? and bookId = ?", new Object[]{p.getBranchId(),b.getBookId()});
				booksCount.put(b, count);
			}
			p.setBooksCount(booksCount);
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
