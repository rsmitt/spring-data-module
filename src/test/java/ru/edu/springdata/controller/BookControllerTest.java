package ru.edu.springdata.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.service.impl.BookServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    private final MockMvc mockMvc;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final BookServiceImpl bookServiceImpl;

    private final static String URL = "/books";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS books";
    private final static String CREATE_TABLE = """
                                    CREATE TABLE books
                                    (id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     language VARCHAR(64)  NOT NULL,
                                     category VARCHAR(64)
                                    )
            """;

    private final static String INSERT_BOOKS = """
                                    INSERT INTO books (name, language, category)
                                        VALUES ('Алые паруса', 'русский', 'роман'),
                                               ('Человек-амфибия', 'русский', 'роман'),
                                               ('Чистый код. Создание, анализ и рефакторинг', 'русский', 'it'),
                                               ('SQL. Сборник рецептов', 'русский', 'it'),
                                               ('История России с древнейших времен', 'русский', 'история'),
                                               ('Петр Первый', 'русский', 'история'),
                                               ('Война и мир', 'русский', 'роман')
            """;

    @Autowired
    public BookControllerTest(MockMvc mockMvc,
                              JdbcTemplate jdbcTemplate,
                              ObjectMapper objectMapper,
                              BookServiceImpl bookServiceImpl) {
        this.mockMvc = mockMvc;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.bookServiceImpl = bookServiceImpl;
    }


    @BeforeEach
    void setup() {
        jdbcTemplate.update(DROP_TABLE);
        jdbcTemplate.update(CREATE_TABLE);
        jdbcTemplate.update(INSERT_BOOKS);
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(get(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Алые паруса"))
                .andExpect(jsonPath("$.language").value("русский"))
                .andExpect(jsonPath("$.category").value("роман"));

        mockMvc.perform(get(URL + "/get/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBooksTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(URL + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andReturn();
        List<Book> books = bookToList(mvcResult);
        Book book = books.stream().filter(b -> b.getId() == 6).findFirst().orElse(null);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Петр Первый", book.getName());
    }

    @Test
    void searchByCategoryTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(URL + "/category/it")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
        List<Book> books = bookToList(mvcResult);
        Book book = books.stream().filter(b -> b.getId() == 3).findFirst().orElse(null);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Чистый код. Создание, анализ и рефакторинг", book.getName());
    }

    @Test
    void searchByLanguageTest() throws Exception {
        MvcResult result = mockMvc.perform(get(URL + "/language/русский")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andReturn();
        List<Book> books = bookToList(result);
        Book book = books.get(0);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Алые паруса", book.getName());
    }

    @Test
    void searchTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(URL + "/search?language=русский&category=роман")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();
        List<Book> books = bookToList(mvcResult);
        Book book = books.stream().filter(b -> b.getId() == 1).findFirst().orElse(null);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Алые паруса", book.getName());
    }

    @Test
    void addRecordTest() throws Exception {
        long oldRecordCount = bookServiceImpl.getRecordCount();
        Book newBook = new Book();
        newBook.setName("Остров сокровищ");
        newBook.setLanguage("Русский");
        newBook.setCategory("Приключения");
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk());
        Assertions.assertEquals(oldRecordCount + 1, bookServiceImpl.getRecordCount());
        List<Book> books = bookServiceImpl.getAllBooks();
        Book book = books.stream().max(Comparator.comparingLong(Book::getId)).orElse(null);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Остров сокровищ", book.getName());
    }

    @Test
    void updateRecordTest() throws Exception {
        long oldRecordCount = bookServiceImpl.getRecordCount();
        Book book = bookServiceImpl.getBook(2);
        book.setName("Человек-амфибия");
        mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
        Assertions.assertEquals(oldRecordCount, bookServiceImpl.getRecordCount());
        Book newBook = bookServiceImpl.getBook(2);
        Assertions.assertNotNull(book);
        Assertions.assertEquals("Человек-амфибия", newBook.getName());
    }

    @Test
    void deleteRecordTest() throws Exception {
        long oldRecordCount = bookServiceImpl.getRecordCount();
        mockMvc.perform(delete(URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(oldRecordCount - 1, bookServiceImpl.getRecordCount());
        List<Book> books = bookServiceImpl.getAllBooks();
        Book book = books.stream().filter(b -> b.getId() == 5).findFirst().orElse(null);
        Assertions.assertNull(book);
    }

    private List<Book> bookToList(MvcResult mvcResult) throws Exception {
        String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(response, new TypeReference<>() {});
    }
}