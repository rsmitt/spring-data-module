package ru.edu.springdata.repository;

import ru.edu.springdata.model.Book;
import ru.edu.springdata.model.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BookRepository {
    Stream<Book> findAll(Page page);

    Optional<Book> findById(long id);

    List<Book> findByLanguageAndCategory(String language, String category);

    Long save(Book book);

    int update(Book book);

    int deleteById(long id);
}
