package com.epam.books.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksRequest {

    @NotNull
    private String name;
    @NotNull
    private String publisher;
    @NotNull
    private String author;
}
