package ru.edu.springdata.dao;

import ru.edu.springdata.model.Book;

import java.util.List;

public interface BookDao {
    Book addBook(Book book);

    void deleteBook(Long id);

    void update(Book book);

    List<Book> getByLanguage(String language);

    List<Book>  getByCategory(String category);
}
