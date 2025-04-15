package com.epam.library.services;



import com.epam.library.dto.BooksRequest;
import com.epam.library.dto.BooksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "BOOKS", path = "/v1/books")
public interface BooksClient {

    @GetMapping
    ResponseEntity<List<BooksResponse>> getAllBooks();

    @GetMapping("/{id}")
    ResponseEntity<BooksResponse> getBookById(@PathVariable("id") Long id);

    @PostMapping
    ResponseEntity<BooksResponse> saveBook(@RequestBody BooksRequest booksRequest);

    @PutMapping("/{id}")
    ResponseEntity<BooksResponse> updateBook(@PathVariable("id") Long id, @RequestBody BooksRequest booksRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable("id") Long id);
}

