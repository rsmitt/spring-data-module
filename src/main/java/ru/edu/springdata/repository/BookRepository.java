package ru.edu.springdata.repository;

import ru.edu.springdata.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    /**
     * Найти все книги в БД
     */
    List<Book> findAll();

    /**
     * Найти книгу по её id
     * @param id
     */
    Optional<Book> findById(Long id);

    /**
     * Сохранить книгу в БД
     * @param book
     */
    Book save(Book book);

    /**
     * Обновить существующую книгу в БД
     * @param book
     */
    void update(Book book);

    /**
     * Удалить книгу по её id
     * @param id
     */
    void delete(Long id);

    /**
     * Получить количество записей
     */
    long getNumberOfRecords();

    /**
     * Найти книгу по языку написания
     * @param language
     */
    List<Book> findBookByLanguage(String language);

    /**
     * Найти книгу по категории
     * @param category
     */
    List<Book> findBookByCategory(String category);

    /**
     * Найти книгу по категории и языку написания
     * @param language
     * @param category
     */
    List<Book> findBookByLanguageAndCategory(String language, String category);

}