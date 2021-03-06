package com.intern.book.services.servicesIplm;

import com.intern.book.converter.bases.Converter;
import com.intern.book.exeptions.NotFoundException;
import com.intern.book.models.dao.Book;
import com.intern.book.models.dto.BookDto;
import com.intern.book.repositories.BookRepository;
import com.intern.book.services.BookService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<BookDto> getAllBooks(Pageable pageable) {
        return bookDaoToBookDtoConverter.convert(bookRepository.findAll(pageable).getContent());
    }

    @Override
    @Transactional
    public BookDto getBookById(Integer bookId) {
        if (checkBookExistById(bookId)) {
            return bookDaoToBookDtoConverter.convert(bookRepository.findById(bookId).get());
        }
        throw new NotFoundException("Book not found with id: " + bookId);
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
        if (checkBookExistById(bookId)) {
            String usernameCurrent = userService.getCurrentUser().getUsername();
            String usernameCreatedBook = bookRepository.findById(bookId).get().getUser().getUsername();
            return usernameCurrent == usernameCreatedBook;
        }
        throw new NotFoundException("Book not found with id: " + bookId);
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

    @Override
    public List<BookDto> getMyBooks(Pageable pageable) {
        return bookDaoToBookDtoConverter.convert(bookRepository.findAllByUser(userService.getCurrentUser(), pageable).getContent());
    }

    @Override
    public List<BookDto> getAllBooksEnabled() {
        return bookDaoToBookDtoConverter.convert(bookRepository.findAllByEnabled(true));
    }

    @Override
    public List<BookDto> search(String keyword) {
        return bookDaoToBookDtoConverter.convert(bookRepository.findDistinctByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseAndEnabled(keyword, keyword, true));
    }

    @Override
    public List<BookDto> getAllBooksDisabled() {
        return bookDaoToBookDtoConverter.convert(bookRepository.findAllByEnabled(false));
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public boolean checkBookExistById(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return true;
        }
        return false;
    }

}
