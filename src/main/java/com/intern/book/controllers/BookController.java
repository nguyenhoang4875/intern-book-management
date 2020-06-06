package com.intern.book.controllers;

import com.intern.book.exeptions.NotAuthorizedException;
import com.intern.book.models.dto.BookDto;
import com.intern.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public BookDto getBookbyId(@PathVariable Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public BookDto addBook(@Valid @RequestBody BookDto bookDto) {
        bookDto.setId(0);
        return bookService.addBook(bookDto);
    }

    @PutMapping("/{bookId}")
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable Integer bookId) {
        if (bookService.checkCanEditBook(bookId)) {
            bookDto.setId(bookId);
            return bookService.updateBook(bookDto);
        }
        throw new NotAuthorizedException("You don't have permit to edit this book");
    }
}
