package ru.edu.springdata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping(value = "/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PatchMapping(value = "/update")
    public void updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PostMapping(value = "/getByLanguage/{language}")
    public List<Book> getByLanguage(@PathVariable String language) {
        return bookService.getByLanguage(language);
    }

    @PostMapping(value = "/getByCategory/{category}")
    public List<Book> getByCategory(@PathVariable String category) {
        return bookService.getByCategory(category);
    }
}
