package ru.edu.springdata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.springdata.model.Book;
import ru.edu.springdata.service.impl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BookServiceImpl bookServiceImpl;

    public BooksController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book = bookServiceImpl.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        bookServiceImpl.updateBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookServiceImpl.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBook() {
        return ResponseEntity.ok(bookServiceImpl.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Book book = bookServiceImpl.getBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestParam String language,
                                             @RequestParam String category) {
        return ResponseEntity.ok(bookServiceImpl.getBooksByLanguageAndCategory(language, category));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<Book>> searchByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(bookServiceImpl.getBooksByLanguage(language));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> searchBookByCategory(@PathVariable String category) {
        return ResponseEntity.ok(bookServiceImpl.getBooksByCategory(category));
    }
}
