package com.epam.library.repository;

import com.epam.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    long countByUsername(String username);

    @Query("SELECT l.bookId FROM Library l WHERE l.username = :username")
    List<Long> findAllBookIdsByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Library l WHERE l.bookId = :bookId")
    void deleteByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Library l WHERE l.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Library l WHERE l.bookId = :bookId AND l.username = :username")
    void deleteByBookIdAndUsername(@Param("bookId") Long bookId, @Param("username") String username);
}
