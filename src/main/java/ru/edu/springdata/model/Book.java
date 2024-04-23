package ru.edu.springdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Book {

    private Long id;
    private String name;
    private String language;
    private String category;
}
