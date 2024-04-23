package ru.edu.springdata.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.edu.springdata.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String language = rs.getString("language");
        String category = rs.getString("category");

        return new Book(id, name, language, category);
    }
}
