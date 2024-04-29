package ru.edu.springdata.model;

import lombok.Data;
import lombok.ToString;


/**
 * Сущность описывающая книги.
 * Между книгами и категориями связь один ко многим.
 * Книга может быть только в одной категории. В одной категории может быть множество книг.
 * <p>
 * Между книгами и авторами связь многие ко многим.
 * Между авторами и адресами свзяь один к одному
 */

@Data
@ToString
public class Book {

    private Long id;

    private String title;

    private String language;

    private Category category;

    private boolean active;
}

