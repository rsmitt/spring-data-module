package ru.edu.springdata.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.repository.BookRepository;
import ru.edu.springdata.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findBookByLanguage(language);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findBookByCategory(category);
    }

    public List<Book> getBooksByLanguageAndCategory(String language, String category) {
        return bookRepository.findBookByLanguageAndCategory(language, category);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    public long getRecordCount() {
        return bookRepository.getNumberOfRecords();
    }
}