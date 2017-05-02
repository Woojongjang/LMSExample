package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class BookDAO extends BaseDAO{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book (title) values (?)", new Object[] {book.getTitle()});
	}
	
	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException{
		return saveWithID("insert into tbl_book (title) values (?)", new Object[] {book.getTitle()});
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_authors values (?, ?)", new Object[] {bookId, authorId});
	}
	
	public void addBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_genres values (?, ?)", new Object[] {genreId, bookId});
	}
	
	public void addBookPublisher(Integer bookId, Integer pubId) throws ClassNotFoundException, SQLException{
		save("update tbl_book set pubId = ? where bookId = ?", new Object[] {pubId, bookId});
	}
	
	public void deleteBookAuthors(Integer bookId) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_authors where bookId = ?", new Object[] {bookId});
	}
	
	public void deleteBookGenres(Integer bookId) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_genres where bookId = ?", new Object[] {bookId});
	}
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		List<Author> bookAuthors = book.getAuthors();
		List<Genre> bookGenres = book.getGenres();
		if(bookAuthors!=null && !bookAuthors.isEmpty()) {
			deleteBookAuthors(book.getBookId());
			for(Author bAuthElem : bookAuthors) {
				addBookAuthors(book.getBookId(),bAuthElem.getAuthorId());
			}
		}
		else {
			deleteBookAuthors(book.getBookId());
		}
		if(bookGenres!=null && !bookGenres.isEmpty()) {
			deleteBookGenres(book.getBookId());
			for(Genre bGenreElem : bookGenres) {
				addBookGenres(book.getBookId(),bGenreElem.getGenreId());
			}
		}
		else {
			deleteBookGenres(book.getBookId());
		}
		if(book.getPublisher()!=null) {
//			System.out.println("UPDATING PUB ID:" +book.getPublisher().getPublisherId());
//			System.out.println("UPDATING BOOK ID:" +book.getBookId());
			save("update tbl_book set pubId = ? where bookId = ?", new Object[]{book.getPublisher().getPublisherId(), book.getBookId()});
		}
		else {
			save("update tbl_book set pubId = ? where bookId = ?", new Object[]{null, book.getBookId()});
		}
		save("update tbl_book set title = ? where bookId = ?", new Object[]{book.getTitle(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public Book readBookByID(Integer bookID) throws ClassNotFoundException, SQLException{
		List<Book> books = read("select * from tbl_book where bookId = ?", new Object[]{bookID});
		if(books!=null && !books.isEmpty()){
			return books.get(0);
		}
		return null;
	}
	
	public List<Book> readBooksByName(Integer pageNo, String  bookName) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageSize(10);
			setPageNo(pageNo);
		}
		bookName = "%"+bookName+"%";
		List<Book> retList = read("select * from tbl_book where title like ?", new Object[]{bookName});
		return retList;
	}
	
	public List<Book> readAllBooks(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		//List<Book> bookRead = read("select * from tbl_book", null);
		return read("select * from tbl_book", null);
	}

	public Integer getBooksCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_book", null);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		List<Publisher> pubs = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		Publisher bookPub = new Publisher();
		while(rs.next()){
			Book b = new Book();
			b.setTitle(rs.getString("title"));
			b.setBookId(rs.getInt("bookId"));
			Integer pubId = rs.getInt("pubId");
			//b.setPublisher(pdao.readPublisherByID(pubId));
//			if(rs.getInt("bookId")==46) {
//				System.out.println("PUBLISHER ID: "+rs.getInt("pubId"));
//				System.out.println("PUBLISHER ID: "+pubId);
//			}
			if(pubId != 0) {
//				pubs = pdao.readFirstLevel("select * from tbl_publisher where publisherId = ?", new Object[]{pubId});
//				if(pubs != null && !pubs.isEmpty()) {
//					if(rs.getInt("bookId")==46) {
//						System.out.println("PUB NAME: "+pubs.get(0).getPublisherName());
//					}
//					bookPub =  pubs.get(0);
//					b.setPublisher(pubs.get(0));
//				}
//				else {
//					bookPub.setPublisherId(0);
//					bookPub.setPublisherName("NO PUBLISHER");
//					bookPub.setPublisherAddress("NO ADDRESS");
//					bookPub.setPublisherPhone("NO PHONE");
//					b.setPublisher(bookPub);
//				}
				b.setPublisher(pdao.readPublisherByID(pubId));
			}
			else {
				bookPub.setPublisherId(0);
				bookPub.setPublisherName("NO PUBLISHER");
				bookPub.setPublisherAddress("NO ADDRESS");
				bookPub.setPublisherPhone("NO PHONE");
				b.setPublisher(bookPub);
			}
			//b.setPublisher((Publisher) pdao.readFirstLevel("select * from tbl_publisher where publisherId = ?)", new Object[]{rs.getInt("pubId")}).get(0));
			b.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId IN (Select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			b.setGenres(gdao.readFirstLevel("select * from tbl_genre where genre_id IN (Select genre_id from tbl_book_genres where bookId = ?);", new Object[]{b.getBookId()}));
			books.add(b);
		}
		return books;
	}
	
	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setTitle(rs.getString("title"));
			b.setBookId(rs.getInt("bookId"));
			books.add(b);
		}
		return books;
	}
}
