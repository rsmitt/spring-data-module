package ru.edu.springdata.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    private Long id;
    private String name;
    private String language;
    private String category; // history, it, health etc...
}
