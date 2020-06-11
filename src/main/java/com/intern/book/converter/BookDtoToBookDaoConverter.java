package com.intern.book.converter;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Book;
import com.intern.book.models.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookDtoToBookDaoConverter extends Converter<BookDto, Book> {

    @Override
    public Book convert(BookDto source) {
        Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setDescription(source.getDescription());
        book.setAuthor(source.getAuthor());
        book.setEnabled(source.isEnabled());
        book.setImage(source.getImage());
        return book;
    }
}
