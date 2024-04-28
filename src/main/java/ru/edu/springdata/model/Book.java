package ru.edu.springdata.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;
    private String name;
    private String language;
    private String category;
}
