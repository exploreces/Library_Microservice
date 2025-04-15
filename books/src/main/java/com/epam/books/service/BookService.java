package com.epam.books.service;

import com.epam.books.dto.request.BooksRequest;
import com.epam.books.dto.response.BooksResponse;
import com.epam.books.entity.Book;
import com.epam.books.exception.InvalidDataException;
import com.epam.books.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private BookRepository bookRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }


    public List<BooksResponse> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book-> objectMapper.convertValue(book , BooksResponse.class))
                .toList();
    }


    public BooksResponse getBookById(Long id){
        Book book =  bookRepository.findById(id).orElseThrow(()-> new InvalidDataException("The id does not exist"));
        return objectMapper.convertValue(book , BooksResponse.class);
    }



    public BooksResponse saveBook(BooksRequest booksRequest){
        Book book = objectMapper.convertValue(booksRequest , Book.class);
        if(booksRequest.getName().isEmpty()){
            throw new InvalidDataException("The fields must be filled");
        }
        if(bookRepository.existsByName(booksRequest.getName())){
            throw new InvalidDataException("The book with the same name exists");
        }
        bookRepository.save(book);
        return objectMapper.convertValue(book , BooksResponse.class);

    }

    public void delete(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()->
                new InvalidDataException("This Id does not exist"));
        bookRepository.delete(book);
    }

    public BooksResponse update( Long id  , BooksRequest booksRequest){

        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new InvalidDataException("Please provide correct id as this id does not exist"));

        book.setName(booksRequest.getName());
        book.setAuthor(booksRequest.getAuthor());
        book.setPublisher(booksRequest.getPublisher());
        bookRepository.save(book);
        return objectMapper.convertValue(book , BooksResponse.class);
    }


}
