package com.intern.book.repositories;

import com.intern.book.models.dao.Book;
import com.intern.book.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByUser(User user);

    List<Book> findAllByEnabled(boolean enabled);

    List<Book> findDistinctByAuthorContainingOrTitleContainingIgnoreCase(String title, String author);
}
