package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;
/**
 * This is a DAO
 * @genre ppradhan
 *
 */
public class GenreDAO extends BaseDAO{
	
	public GenreDAO(Connection conn) {
		super(conn);
	}

	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException{
		save("insert into tbl_genre (genre_name) values (?)",
				new Object[] {genre.getGenreName()});
	}
	
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException{
		save("update tbl_genre set genre_name = ? where genre_id = ?", new Object[]{genre.getGenreName(), genre.getGenreId()});
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException{
		save("delete from tbl_genre where genre_id = ?", new Object[] {genre.getGenreId()});
	}
	
	public List<Genre> readAllGenres(Integer pageNo) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		return read("select * from tbl_genre", null);
	}
	
	public Genre readGenreByID(Integer genreId) throws ClassNotFoundException, SQLException{
		List<Genre> genres = read("select * from tbl_genre where genre_id = ?", new Object[]{genreId});
		if(genres!=null && !genres.isEmpty()){
			return genres.get(0);
		}
		return null;
	}
	
	public List<Genre> readGenresByName(String  genreName) throws ClassNotFoundException, SQLException{
		setPageSize(10);
		genreName = "%"+genreName+"%";
		return read("select * from tbl_genre where genre_name like ?", new Object[]{genreName});
	}
	
	public List<Genre> readGenresByName(Integer pageNo, String  genreName) throws ClassNotFoundException, SQLException{
		if(pageNo!=null) {
			setPageNo(pageNo);
			setPageSize(10);
		}
		genreName = "%"+genreName+"%";
		List<Genre> retList = read("select * from tbl_genre where genre_name like ?", new Object[]{genreName});
		return retList;
	}
	
	public Integer getGenresCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as COUNT from tbl_genre", null);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		//BookDAO bdao = new BookDAO(conn);
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre p = new Genre();
			p.setGenreId(rs.getInt("genre_id"));
			String genreName = rs.getString("genre_name");
			if(genreName != null) {
				p.setGenreName(genreName);
			}
			else {
				p.setGenreName("NO GENRE");
			}
			//a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_genres where genre_id = ?)", new Object[]{a.getGenreId()}));
			genres.add(p);
		}
		return genres;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		if(!rs.next()) {
			Genre g = new Genre();
			g.setGenreId(0);
			g.setGenreName("NO GENRE");
			genres.add(g);
		}
		rs.beforeFirst();
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			String genreName = rs.getString("genre_name");
			if(genreName != null) {
				g.setGenreName(genreName);
			}
			else {
				g.setGenreName("NO GENRE");
			}
			genres.add(g);
		}
		return genres;
	}

}
