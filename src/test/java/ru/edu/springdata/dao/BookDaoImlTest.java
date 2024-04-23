package ru.edu.springdata.dao;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.edu.springdata.model.Book;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookDaoImlTest {

    public static final String NAME = RandomStringUtils.randomAlphabetic(10);
    public static final String NAME_2 = RandomStringUtils.randomAlphabetic(10);
    public static final String LANGUAGE = RandomStringUtils.randomAlphabetic(10);
    public static final String LANGUAGE_2 = RandomStringUtils.randomAlphabetic(10);
    public static final String CATEGORY = RandomStringUtils.randomAlphabetic(10);
    public static final String CATEGORY_2 = RandomStringUtils.randomAlphabetic(10);

    @Autowired
    private BookDaoImpl bookDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void create() {
        clearAll();
    }

    @AfterAll
    void clear() {
        clearAll();
    }

    @Test
    void addBookTest() {
        bookDao.addBook(new Book(null, NAME, LANGUAGE, CATEGORY));
        var books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        assertTrue(CollectionUtils.isNotEmpty(books));
        var book = books.get(0);
        assertNotNull(book);
        assertEquals(NAME, book.getName());
        assertEquals(LANGUAGE, book.getLanguage());
        assertEquals(CATEGORY, book.getCategory());

        clearAll();
    }

    @Test
    void deleteBookTest() {
        bookDao.addBook(new Book(null, NAME, LANGUAGE, CATEGORY));
        var books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        assertTrue(CollectionUtils.isNotEmpty(books));

        bookDao.deleteBook(books.get(0).getId());

        books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        assertTrue(CollectionUtils.isEmpty(books));
    }

    @Test
    void updateTest() {
        bookDao.addBook(new Book(null, NAME, LANGUAGE, CATEGORY));
        var books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));

        bookDao.update(new Book(books.get(0).getId(), NAME_2, LANGUAGE_2, CATEGORY_2));
        books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        assertTrue(CollectionUtils.isNotEmpty(books));
        var book = books.get(0);
        assertNotNull(book);
        assertEquals(NAME_2, book.getName());
        assertEquals(LANGUAGE_2, book.getLanguage());
        assertEquals(CATEGORY_2, book.getCategory());

        clearAll();
    }

    @Test
    void getByLanguageTest() {
        bookDao.addBook(new Book(null, NAME, LANGUAGE, CATEGORY));

        var books = bookDao.getByLanguage(LANGUAGE);
        assertTrue(CollectionUtils.isNotEmpty(books));
        var book = books.get(0);
        assertNotNull(book);
        assertEquals(NAME, book.getName());
        assertEquals(LANGUAGE, book.getLanguage());
        assertEquals(CATEGORY, book.getCategory());

        clearAll();
    }

    @Test
    void getByCategoryTest() {
        bookDao.addBook(new Book(null, NAME, LANGUAGE, CATEGORY));

        var books = bookDao.getByCategory(CATEGORY);
        assertTrue(CollectionUtils.isNotEmpty(books));
        var book = books.get(0);
        assertNotNull(book);
        assertEquals(NAME, book.getName());
        assertEquals(LANGUAGE, book.getLanguage());
        assertEquals(CATEGORY, book.getCategory());

        clearAll();
    }

    private void clearAll() {
        jdbcTemplate.update("delete from book");
    }
}
