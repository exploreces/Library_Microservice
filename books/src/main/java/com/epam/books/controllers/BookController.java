package com.epam.books.controllers;

import com.epam.books.dto.request.BooksRequest;
import com.epam.books.dto.response.BooksResponse;
import com.epam.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
public ResponseEntity<List<BooksResponse>> getAllBooks(){
    return ResponseEntity.ok(bookService.getAllBooks());
}

@GetMapping("/{id}")
    public ResponseEntity<BooksResponse> getBookById(@PathVariable("id")Long id){
    return ResponseEntity.ok(bookService.getBookById(id));
    }


@PostMapping
    public ResponseEntity<BooksResponse>saveBook(@RequestBody BooksRequest booksRequest) {
    BooksResponse booksResponse = bookService.saveBook(booksRequest);
    return new ResponseEntity<>(booksResponse, HttpStatus.CREATED);
}

@DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteBook(@PathVariable("id")Long id){
    bookService.delete(id);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

@PutMapping("/{id}")
    public ResponseEntity<BooksResponse>updateBook(@PathVariable("id") Long id , @RequestBody BooksRequest booksRequest){
    BooksResponse booksResponse = bookService.update(id , booksRequest);
    return  new ResponseEntity<>(booksResponse , HttpStatus.OK);

    }


}
