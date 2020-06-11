package com.intern.book.services;

import com.intern.book.models.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Integer bookId);

    BookDto addBook(BookDto bookDto);

    boolean checkCanEditBook(Integer bookId);

    BookDto updateBook(BookDto bookDto);

    List<BookDto> getAllBooksOfUser();

    List<BookDto> getAllBooksEnabled();

    List<BookDto> search(String search);
}
