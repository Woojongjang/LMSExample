package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdminService {
	
	public void addAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.addAuthor(author);
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
	
	public void editAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(author);
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
	
	public void editBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(book);
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
	
	public void addBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.addBookWithID(book);
			List<Author> bookAuthors = book.getAuthors();
			List<Genre> bookGenres = book.getGenres();
			Publisher bookPublisher = book.getPublisher();
			if(bookAuthors!=null && !bookAuthors.isEmpty()) {
				for(Author bAuthElem : bookAuthors) {
					bdao.addBookAuthors(bookId,bAuthElem.getAuthorId());
				}
			}
			if(bookGenres!=null && !bookGenres.isEmpty()) {
				for(Genre bGenreElem : bookGenres) {
					bdao.addBookGenres(bookId,bGenreElem.getGenreId());
				}
			}
			if(bookPublisher!=null) {
				bdao.addBookPublisher(bookId,bookPublisher.getPublisherId());
			}
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
	
	public void addGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.addGenre(genre);
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
	
	public void editGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.updateGenre(genre);
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
	
	public void addPublisher(Publisher publisher) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO gdao = new PublisherDAO(conn);
			gdao.addPublisher(publisher);
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
	
	public void editPublisher(Publisher publisher) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO gdao = new PublisherDAO(conn);
			gdao.updatePublisher(publisher);
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
	
	public void addBorrower(Borrower borrower) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			brdao.addBorrower(borrower);
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
	
	public void editBorrower(Borrower borrower) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			brdao.updateBorrower(borrower);
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
	
	public void addBranch(LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.addBranch(branch);
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
	
	public void editBranch(LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.updateBranch(branch);
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
	
	public List<Book> getAllBooks(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooks(pageNo);
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
	
	public List<Author> getAllAuthors(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthors(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	public List<Genre> getAllGenres(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readAllGenres(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Publisher> getAllPublishers(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readAllPublishers(pageNo);
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
	
	public List<Borrower> getAllBorrowers(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readAllBorrowers(pageNo);
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
	
	public List<LibraryBranch> getAllBranches(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAllBranches(pageNo);
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
	
	public Author getAuthorByPk(Integer authorId) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAuthorByID(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getAuthorsCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.getAuthorsCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBooksCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getBooksCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getGenresCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.getGenresCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getPublishersCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.getPublishersCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBorrowersCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.getBorrowersCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBranchesCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.getBranchesCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBookLoansCount() throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.getPublishersCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void deleteAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void deleteBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void deleteGenre(Genre genre) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.deleteGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			brdao.deleteBorrower(borrower);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void deleteBranch(LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
			pdao.deleteBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}

	/**
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<Author> getAuthorsByName(Integer pageNoThenCount, String authorName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> authList = adao.readAuthorsByName(pageNoThenCount, authorName);
			return authList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	/**
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<Book> getBooksByName(Integer pageNoThenCount, String bookName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			List<Book> bookList = bdao.readBooksByName(pageNoThenCount, bookName);
			return bookList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	/*
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<Genre> getGenresByName(Integer pageNoThenCount, String genreName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			List<Genre> genreList = gdao.readGenresByName(pageNoThenCount, genreName);
			return genreList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	/*
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<Publisher> getPublishersByName(Integer pageNoThenCount, String publisherName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			List<Publisher> publisherList = pdao.readPublishersByName(pageNoThenCount, publisherName);
			return publisherList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	/*
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<Borrower> getBorrowersByName(Integer pageNoThenCount, String borrowerName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			List<Borrower> borrowerList = brdao.readBorrowersByName(pageNoThenCount, borrowerName);
			return borrowerList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	/*
	 * @param pageNo
	 * @param searchString
	 * @return
	 */
	public List<LibraryBranch> getBranchesByName(Integer pageNoThenCount, String branchName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> branchList = pdao.readBranchesByName(pageNoThenCount, branchName);
			return branchList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBranchSearchCount(String branchName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> branchList = pdao.readBranchesByName(null, branchName);
			return branchList.size();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getAuthorSearchCount(String authorName) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> authList = adao.readAuthorsByName(null, authorName);
			return authList.size();
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
