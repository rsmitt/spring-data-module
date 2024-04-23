package ru.edu.springdata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.model.FilterRequest;
import ru.edu.springdata.model.Page;
import ru.edu.springdata.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    public final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestBody Page page) {
        return ResponseEntity.ok(bookService.findAllBooks(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/getByLanguageAndCategory")
    public ResponseEntity<List<Book>> getBooksByLanguageAndCategory(@RequestBody FilterRequest request) {
        return ResponseEntity.ok(bookService.findBookByLanguageAndCategory(request));
    }

    @PostMapping
    public ResponseEntity<Long> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PatchMapping
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        return ResponseEntity.ok(bookService.deleteBookById(id));
    }}

