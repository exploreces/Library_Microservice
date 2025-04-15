package com.epam.books.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksResponse {

    private Long id;
    private String name;
    private String publisher;
    private String author;
}
