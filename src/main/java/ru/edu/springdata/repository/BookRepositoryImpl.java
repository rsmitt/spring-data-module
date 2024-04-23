package ru.edu.springdata.repository;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.edu.springdata.mapper.BookMapper;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.model.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert insert;

    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insert = new SimpleJdbcInsert(jdbc.getJdbcTemplate());;
        this.insert.setTableName("books");
        this.insert.usingGeneratedKeyColumns("id");
    }

    @Override
    public Stream<Book> findAll(Page page) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("limit", page.limit());
        paramMap.put("offset", page.offset());
        return jdbc.queryForStream("select * from books limit :limit offset :offset", paramMap, new BookMapper());
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return Optional.ofNullable(jdbc.queryForObject("select * from books where id = :id", params, new BookMapper()));
    }

    @Override
    public List<Book> findByLanguageAndCategory(String language, String category) {
        Map<String, Object> params = new HashMap<>();
        params.put("language", language);
        params.put("category", category);
        return jdbc.query("select * from books where language = :language and category = :category",
                params, new BookMapper());
    }

    @Override
    public Long save(Book book) {
        return insert.executeAndReturnKey(new BeanPropertySqlParameterSource(book)).longValue();
    }

    @Override
    public int update(Book book) {
        return jdbc.update(
                "UPDATE books SET name = :name, language = :language, category = :category WHERE ID = :id",
                new BeanPropertySqlParameterSource(book)
        );
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.update(
                "DELETE FROM books WHERE id = :id", params);
    }
}
