package ru.edu.springdata.repository.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.repository.BookRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Найти книгу по языку написания
     * @param language
     */
    @Override
    public List<Book> findBookByLanguage(String language) {
        return jdbcTemplate.query("SELECT * FROM books WHERE language = ?", new BeanPropertyRowMapper<>(Book.class),
                language);
    }

    /**
     * Найти книгу по языку категории
     * @param category
     */
    @Override
    public List<Book> findBookByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM books WHERE category = ?", new BeanPropertyRowMapper<>(Book.class),
                category);
    }

    /**
     * Найти книгу по категории и языку написания
     * @param language
     * @param category
     */
    @Override
    public List<Book> findBookByLanguageAndCategory(String language, String category) {
        return jdbcTemplate.query("SELECT * FROM books WHERE language = ? AND category = ?",
                new BeanPropertyRowMapper<>(Book.class), language, category);
    }

    /**
     * Отобразить все книги
     */
    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    /**
     * Найти книгу по id
     * @param id
     */
    @Override
    public Optional<Book> findById(Long id) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE id = ?",
                new BeanPropertyRowMapper<>(Book.class), id);
        if (books.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(books.get(0));
    }

    /**
     * Сохранить книгу в БД
     * @param book
     */
    @Override
    public Book save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSQL = "INSERT INTO books(name, language, category) VALUES (?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSQL, new String[]{"id"});
            ps.setString(1, book.getName());
            ps.setString(2, book.getLanguage());
            ps.setString(3, book.getCategory());
            return ps;
        }, keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return book;
    }

    /**
     * Обновить существующую книгу
     * @param book
     */
    @Override
    public void update(Book book) {
        jdbcTemplate.update("UPDATE books SET name = ?, category = ?, language = ? WHERE id = ?",
                book.getName(), book.getCategory(), book.getLanguage(), Objects.requireNonNull(book.getId()));
    }

    /**
     * Удалить книгу по id
     * @param id
     */
    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    /**
     * Вернуть количество записей из БД
     */
    public long getNumberOfRecords() {
        return Objects.requireNonNull(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM books", Long.class));
    }
}