package com.intern.book.repositories;

import com.intern.book.models.dao.Book;
import com.intern.book.models.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByUser(User user, Pageable pageable);

    List<Book> findAllByEnabled(boolean enabled);

    List<Book> findDistinctByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseAndEnabled(String title, String author, boolean enabled);
}
