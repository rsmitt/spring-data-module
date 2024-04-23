package ru.edu.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.springdata.dao.BookDao;
import ru.edu.springdata.model.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private BookDao bookDao;

    @Override
    public Book addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteBook(id);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public List<Book> getByLanguage(String language) {
        return bookDao.getByLanguage(language);
    }

    @Override
    public List<Book>  getByCategory(String category) {
        return bookDao.getByCategory(category);
    }
}
