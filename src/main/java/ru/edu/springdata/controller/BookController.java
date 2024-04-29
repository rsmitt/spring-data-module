package ru.edu.springdata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public int addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping(value = "/update/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book book) {
        bookService.updateBook(id, book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/category/{category}")
    public List<Book> getAllBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/language/{language}")
    public List<Book> getAllBooksByLanguage(@PathVariable String language) {
        return bookService.getBooksByLanguage(language);
    }
}
