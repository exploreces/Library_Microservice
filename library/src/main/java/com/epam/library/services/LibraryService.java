package com.epam.library.services;

import com.epam.library.dto.BooksResponse;
import com.epam.library.dto.LibraryResponse;
import com.epam.library.entity.Library;
import com.epam.library.repository.LibraryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private ObjectMapper objectMapper;

    private final BooksClient bookClient;
    private final UserClient userClient;
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(BooksClient bookClient, UserClient userClient, LibraryRepository libraryRepository) {
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.libraryRepository = libraryRepository;
    }

    public LibraryResponse issueBooks(String username, Long bookId) {
        userClient.getByName(username).getBody();
        bookClient.getBookById(bookId).getBody();

        long count = libraryRepository.countByUsername(username);
        if (count >= 3) {
            throw new RuntimeException("User already has 3 books issued.");
        }

        Library library = new Library();
        library.setUsername(username);
        library.setBookId(bookId);
        libraryRepository.save(library);

        List<Long> bookIds = libraryRepository.findAllBookIdsByUsername(username);

        LibraryResponse response = new LibraryResponse();
        response.setUsername(username);
        response.setBookId(bookIds);
        return response;
    }

    public List<BooksResponse> getIssuedBooks(String username) {
        userClient.getByName(username).getBody();
        List<Long> bookIds = libraryRepository.findAllBookIdsByUsername(username);

        if (bookIds.isEmpty()) {
            throw new RuntimeException("No books issued to this user.");
        }

        return bookIds.stream()
                .map(b -> bookClient.getBookById(b).getBody())
                .toList();
    }

    public void deleteBook(Long id) {
        libraryRepository.deleteByBookId(id);
        bookClient.deleteBook(id);
    }

    public void deleteUser(String username) {
        libraryRepository.deleteByUsername(username);
        userClient.deleteUser(username);
    }

    public void releaseBook(String username, Long id) {
        libraryRepository.deleteByBookIdAndUsername(id, username);
    }
}
