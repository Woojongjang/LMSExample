package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;
/**
 * This is a DAO
 * @borrower woojong
 *
 */
public class BorrowerDAO extends BaseDAO{
	
	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		save("insert into tbl_borrower (name, address, phone) values (?,?,?)",
				new Object[] {borrower.getBorrowerName(),borrower.getBorrowerAddress(),borrower.getBorrowerPhone()});
	}
	
	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[]{borrower.getBorrowerName(), borrower.getBorrowerAddress(),borrower.getBorrowerPhone(), borrower.getBorrowerId()});
	}
	
	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		save("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getBorrowerId()});
	}
	
	public List<Borrower> readAllBorrowers(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		return read("select * from tbl_borrower", null);
	}
	
	public Borrower readBorrowerByID(Integer borrowerID) throws ClassNotFoundException, SQLException{
		List<Borrower> borrowers = read("select * from tbl_borrower where cardNo = ?", new Object[]{borrowerID});
		if(borrowers!=null && !borrowers.isEmpty()){
			return borrowers.get(0);
		}
		return null;
	}
	
	public List<Borrower> readBorrowersByName(String name) throws ClassNotFoundException, SQLException{
		setPageSize(10);
		name = "%"+name+"%";
		return read("select * from tbl_borrower where name like ?", new Object[]{name});
	}
	
	public List<Borrower> readBorrowersByName(Integer pageNo, String name) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		name = "%"+name+"%";
		List<Borrower> retList = read("select * from tbl_borrower where name like ?", new Object[]{name});
		return retList;
	}
	
	public Integer getBorrowersCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_borrower", null);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		//BookDAO bdao = new BookDAO(conn);
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower p = new Borrower();
			p.setBorrowerId(rs.getInt("cardNo"));
			String name = rs.getString("name");
			String addr = rs.getString("address");
			String phone = rs.getString("phone");
			if(name == null){
				p.setBorrowerName("NO BORROWER NAME");
			}
			else if(name.equals("")){
				p.setBorrowerName("NO BORROWER NAME");
			}
			else {
				p.setBorrowerName(name);
			}
			if(addr == null){
				p.setBorrowerAddress("NO BORROWER ADDRESS");
			}
			else if(addr.equals("")){
				p.setBorrowerAddress("NO BORROWER ADDRESS");
			}
			else {
				p.setBorrowerAddress(addr);
			}
			if(phone == null){
				p.setBorrowerPhone("NO BORROWER PHONE");
			}
			else if(phone.equals("")){
				p.setBorrowerPhone("NO BORROWER PHONE");
			}
			else {
				p.setBorrowerPhone(phone);
			}
			//p.setBooks(books);
			borrowers.add(p);
		}
		return borrowers;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower p = new Borrower();
			p.setBorrowerId(rs.getInt("cardNo"));
			p.setBorrowerName(rs.getString("name"));
			p.setBorrowerAddress(rs.getString("address"));
			p.setBorrowerPhone(rs.getString("phone"));
			borrowers.add(p);
		}
		return borrowers;
	}

}
