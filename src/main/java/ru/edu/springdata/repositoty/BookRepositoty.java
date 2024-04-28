package ru.edu.springdata.repositoty;

import ru.edu.springdata.model.Book;

import java.util.List;

public interface BookRepositoty {

    List<Book> findAll();
    Book addBook(Book book);

    void deleteBook(Long id);

    void update(Book book);

    List<Book> getByLanguage(String language);

    List<Book>  getByCategory(String category);
}
