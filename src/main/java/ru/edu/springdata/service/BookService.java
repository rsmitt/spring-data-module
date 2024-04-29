package ru.edu.springdata.service;

import ru.edu.springdata.model.Book;

import java.util.List;

public interface BookService {

    int addBook(Book book);

    int updateBook(Long id, Book book);

    int deleteBook(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByCategory(String category);

    List<Book> getBooksByLanguage(String language);

}
