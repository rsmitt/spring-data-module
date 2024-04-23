package ru.edu.springdata.service;

import org.springframework.stereotype.Service;
import ru.edu.springdata.exeption.BookNotFoundException;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.model.FilterRequest;
import ru.edu.springdata.model.Page;
import ru.edu.springdata.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks(Page page) {
        return bookRepository.findAll(page).toList();
    }

    public Book findBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' not found."));
    }

    public List<Book> findBookByLanguageAndCategory(FilterRequest request) {
        return bookRepository.findByLanguageAndCategory(request.language(), request.category());
    }

    public Long saveBook(Book book) {
        return bookRepository.save(book);
    }

    public String updateBook(Book book) {
        if (bookRepository.update(book) == 1) {
            return "Book successfully updated";
        } else {
            return "Book wasn't updated";
        }
    }

    public String deleteBookById(Long id) {
        if (bookRepository.deleteById(id) == 1) {
            return "Book successfully deleted";
        } else {
            return "Book wasn't deleted";
        }
    }
}
