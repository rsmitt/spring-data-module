package ru.edu.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public int addBook(Book book) {
        return bookRepository.addBook(book);
    }

    @Override
    public int updateBook(Long id, Book book) {
        return bookRepository.updateBook(id, book);
    }

    @Override
    public int deleteBook(Long id) {
        return bookRepository.deleteBook(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findAllByCategory(category);
    }

    @Override
    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findAllByLanguage(language);
    }
}
