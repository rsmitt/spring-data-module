package ru.edu.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.repositoty.BookRepositoty;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoty repositoty;

    public List<Book> findAll() {
        return repositoty.findAll();
    }

    public Book addBook(Book book) {
        return repositoty.addBook(book);
    }

    public void deleteBookById(Long id) {
        repositoty.deleteBook(id);
    }

    public void updateBook(Book book) {
        repositoty.update(book);
    }

    public List<Book> getByLanguage(String language) {
        return  repositoty.getByLanguage(language);
    }

    public List<Book> getByCategory(String category) {
        return repositoty.getByCategory(category);
    }
}


