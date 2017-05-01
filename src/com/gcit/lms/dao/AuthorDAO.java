package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Genre;
/**
 * This is a DAO
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO{
	
	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public List<Author> readAllAuthors(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo != null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		return read("select * from tbl_author", null);
	}
	
	public Author readAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException{
		List<Author> authors = read("select * from tbl_author where authorId = ?", new Object[]{authorID});
		if(authors!=null && !authors.isEmpty()){
			return authors.get(0);
		}
		return null;
	}
	
	public List<Author> readAuthorsByName(String  authorName) throws ClassNotFoundException, SQLException{
		setPageSize(10);
		authorName = "%"+authorName+"%";
		return read("select * from tbl_author where authorName like ?", new Object[]{authorName});
	}
	
	public List<Author> readAuthorsByName(Integer pageNo, String  authorName) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageSize(10);
			setPageNo(pageNo);
		}
		authorName = "%"+authorName+"%";
		List<Author> retList = read("select * from tbl_author where authorName like ?", new Object[]{authorName});
		return retList;
	}
	
	
	public Integer getAuthorsCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_author", null);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		BookDAO bdao = new BookDAO(conn);
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			String authName = rs.getString("authorName");
			if(authName != null) {
				a.setAuthorName(authName);
			}
			else {
				a.setAuthorName("NO AUTHOR");
			}
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)", new Object[]{a.getAuthorId()}));
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		if(!rs.next()) {
			Author a = new Author();
			a.setAuthorId(0);
			a.setAuthorName("NO AUTHOR");
			authors.add(a);
		}
		rs.beforeFirst();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			String authName = rs.getString("authorName");
			if(authName != null) {
				a.setAuthorName(authName);
			}
			else {
				a.setAuthorName("NO AUTHOR");
			}
			authors.add(a);
		}
		return authors;
	}
}
