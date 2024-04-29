package ru.edu.springdata.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edu.springdata.model.Book;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public int addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO book (name, category, language) VALUES (?, ?, ?)", book.getName(), book.getCategory(), book.getLanguage());
    }

    public int deleteBook(Long id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public int updateBook(Long id, Book book) {
        return jdbcTemplate.update("UPDATE book SET name = ?, category = ?, language = ? WHERE id = ?", book.getName(), book.getCategory(), book.getLanguage(), id);
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper(Book.class));
    }

    public List<Book> findAllByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM book WHERE category = ?", new BeanPropertyRowMapper(Book.class), category);
    }

    public List<Book> findAllByLanguage(String language) {
        return jdbcTemplate.query("SELECT * FROM book WHERE language = ?", new BeanPropertyRowMapper(Book.class), language);
    }

}
