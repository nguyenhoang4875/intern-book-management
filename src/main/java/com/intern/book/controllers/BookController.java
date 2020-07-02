package com.intern.book.controllers;

import com.intern.book.exeptions.NotAuthorizedException;
import com.intern.book.models.dto.BookDto;
import com.intern.book.services.BookService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/enabled")
    public List<BookDto> getAllBooksEnabled() {
        return bookService.getAllBooksEnabled();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/disabled")
    public List<BookDto> getAllBooksDisabled() {
        return bookService.getAllBooksDisabled();
    }

    @Secured("ROLE_USER")
    @GetMapping("/mybooks")
    public List<BookDto> getMyBooks(Pageable pageable) {
        return bookService.getMyBooks(pageable);
    }

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping("/search")
    public List<BookDto> search(@RequestParam String keyword) {
        return bookService.search(keyword);
    }

    @PostMapping
    public BookDto addBook(@Valid @RequestBody BookDto bookDto) {
        bookDto.setId(0);
        return bookService.addBook(bookDto);
    }

    @PutMapping("/{bookId}")
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable Integer bookId) {
        if (bookService.checkCanEditBook(bookId) || userService.checkRoleAdmin()) {
            bookDto.setId(bookId);
            return bookService.updateBook(bookDto);
        }
        throw new NotAuthorizedException("You don't have permit to edit this book");
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Integer bookId) {
        if (bookService.checkCanEditBook(bookId) || userService.checkRoleAdmin()) {
            bookService.deleteBook(bookId);
        } else {
            throw new NotAuthorizedException("You don't have permit to edit this book");
        }
    }

    @PutMapping("update-status/{bookId}")
    public boolean updateStatusBook(@PathVariable Integer bookId) {
        System.out.println("book id: "+bookId);
        return bookService.updateStatusBook(bookId);
    }

}
