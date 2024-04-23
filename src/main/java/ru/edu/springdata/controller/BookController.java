package ru.edu.springdata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private BookService bookService;


    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Employee with id = " + id + " was deleted";
    }

    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book) {
        bookService.update(book);
        return book;
    }

    @PostMapping("/get/{language}/by-language")
    public List<Book> getByLanguage(String language) {
        return bookService.getByLanguage(language);
    }

    @PostMapping("/get/{category}/by-category")
    public List<Book>  getByCategory(@PathVariable String category) {
        return bookService.getByCategory(category);
    }
}
