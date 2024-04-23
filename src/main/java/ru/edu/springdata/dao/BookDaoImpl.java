package ru.edu.springdata.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.edu.springdata.model.Book;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoImpl implements BookDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Book addBook(Book book) {
        jdbcTemplate.update("insert into book(name, language, category) values (?, ?, ?)", book.getName(), book.getLanguage(), book.getCategory());
        return book;
    }

    @Override
    public void deleteBook(Long id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("update book set name = ?, language = ?, category = ? where id = ?", book.getName(), book.getLanguage(), book.getCategory(), book.getId());
    }

    @Override
    public List<Book> getByLanguage(String language) {
        return jdbcTemplate.query("select * from book where language = ?", new BeanPropertyRowMapper<>(Book.class), language);
    }

    @Override
    public List<Book>  getByCategory(String category) {
        return jdbcTemplate.query("select * from book where category = ?", new BeanPropertyRowMapper<>(Book.class), category);
    }
}
