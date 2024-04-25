package ru.edu.springdata.service;

import ru.edu.springdata.model.Book;

import java.util.List;

public interface BookService {
    Book getBook(long id);
    List<Book> getAllBooks();
    List<Book> getBooksByLanguage(String language);
    List<Book> getBooksByCategory(String category);
    List<Book> getBooksByLanguageAndCategory(String language, String category);
    Book saveBook(Book book);
    void updateBook(Book book);
    void deleteBook(Long id);
    long getRecordCount();
}
