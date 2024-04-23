package ru.edu.springdata.service;

import ru.edu.springdata.model.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    void deleteBook(Long id);

    void update(Book book);

    List<Book> getByLanguage(String language);

    List<Book>  getByCategory(String category);
}
