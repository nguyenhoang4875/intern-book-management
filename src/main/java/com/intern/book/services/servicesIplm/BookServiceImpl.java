package com.intern.book.services.servicesIplm;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Book;
import com.intern.book.models.dto.BookDto;
import com.intern.book.repositories.BookRepository;
import com.intern.book.services.BookService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Converter<Book, BookDto> bookDaoToBookDtoConverter;

    @Autowired
    private Converter<BookDto, Book> bookDtoToBookDaoConverter;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public List<BookDto> getAllBooks() {
        return bookDaoToBookDtoConverter.convert(bookRepository.findAll());
    }

    @Override
    @Transactional
    public BookDto getBookById(Integer bookId) {
        return bookDaoToBookDtoConverter.convert(bookRepository.findById(bookId).get());
    }

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = bookDtoToBookDaoConverter.convert(bookDto);
        book.setCreatedAt(LocalDateTime.now());
        book.setUser(userService.getCurrentUser());
        bookDto.setId(bookRepository.save(book).getId());
        return bookDaoToBookDtoConverter.convert(book);
    }

    @Override
    @Transactional
    public boolean checkCanEditBook(Integer bookId) {
        String usernameCurrent = userService.getCurrentUser().getUsername();
        String usernameCreatedBook = bookRepository.findById(bookId).get().getUser().getUsername();
        return usernameCurrent == usernameCreatedBook;
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        Book book = bookRepository.getOne(bookDto.getId());
        book.setUpdatedAt(LocalDateTime.now());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(bookDto.getAuthor());
        book.setImage(bookDto.getImage());
        bookRepository.save(book);
        return bookDaoToBookDtoConverter.convert(book);
    }

}