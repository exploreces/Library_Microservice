package com.epam.books.repository;

import com.epam.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select count(b) > 0 from Book b where b.name = :name")
    boolean existsByName(@Param("name") String name);
}
