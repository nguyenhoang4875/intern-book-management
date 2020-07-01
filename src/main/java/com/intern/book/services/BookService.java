package com.intern.book.services;

import com.intern.book.models.dto.BookDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks(Pageable pageable);

    BookDto getBookById(Integer bookId);

    BookDto addBook(BookDto bookDto);

    boolean checkCanEditBook(Integer bookId);

    BookDto updateBook(BookDto bookDto);

    List<BookDto> getMyBooks(Pageable pageable);

    List<BookDto> getAllBooksEnabled();

    List<BookDto> search(String keyword);

    List<BookDto> getAllBooksDisabled();

    void deleteBook(Integer bookId);

    boolean updateStatusBook(Integer bookId);
}
