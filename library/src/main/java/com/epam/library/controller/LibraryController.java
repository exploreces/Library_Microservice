package com.epam.library.controller;

import com.epam.library.dto.*;
import com.epam.library.services.BooksClient;
import com.epam.library.services.LibraryService;
import com.epam.library.services.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    private final BooksClient bookClient;
    private final UserClient userClient;
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(BooksClient bookClient, UserClient userClient, LibraryService libraryService) {
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.libraryService = libraryService;
    }

    // ---------------------------- Book APIs ---------------------------- //

    @GetMapping("/books")
    public ResponseEntity<List<BooksResponse>> getAllBooks() {
        return bookClient.getAllBooks();
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BooksResponse> getBookById(@PathVariable("bookId") Long bookId) {
        return bookClient.getBookById(bookId);
    }

    @PostMapping("/books")
    public ResponseEntity<BooksResponse> addBook(@RequestBody BooksRequest book) {
        BooksResponse savedBook = bookClient.saveBook(book).getBody();
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<BooksResponse> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BooksRequest book) {
        BooksResponse updatedBook = bookClient.updateBook(bookId, book).getBody();
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // ---------------------------- User APIs ---------------------------- //

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return userClient.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable("username") String username) {
        return userClient.getByName(username);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user) {
        UserResponse createdUser = userClient.saveUser(user).getBody();
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("username") String username, @RequestBody UserUpdateRequest user) {
        UserResponse updatedUser = userClient.updateUser(username, user).getBody();
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // ------------------------- Library Actions ------------------------- //

    @PostMapping("/users/{username}/books/{bookId}")
    public ResponseEntity<LibraryResponse> issueBooks(@PathVariable("username") String username,
                                                      @PathVariable("bookId") Long bookId) {
        LibraryResponse response = libraryService.issueBooks(username, bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{username}/books")
    public ResponseEntity<List<BooksResponse>> getIssuedBooks(@PathVariable("username") String username) {
        List<BooksResponse> response = libraryService.getIssuedBooks(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}/books/{bookId}")
    public ResponseEntity<Void> releaseBook(@PathVariable String username, @PathVariable Long bookId) {
        libraryService.releaseBook(username, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        libraryService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
