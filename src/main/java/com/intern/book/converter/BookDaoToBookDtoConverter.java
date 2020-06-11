package com.intern.book.converter;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Book;
import com.intern.book.models.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookDaoToBookDtoConverter extends Converter<Book, BookDto> {

    @Override
    public BookDto convert(Book source) {
        BookDto bookDto = new BookDto();
        bookDto.setId(source.getId());
        bookDto.setTitle(source.getTitle());
        bookDto.setDescription(source.getDescription());
        bookDto.setAuthor(source.getAuthor());
        bookDto.setEnabled(source.isEnabled());
        bookDto.setCreatedAt(source.getCreatedAt());
        bookDto.setUpdatedAt(source.getUpdatedAt());
        bookDto.setImage(source.getImage());
        bookDto.setUsername(source.getUser().getUsername());
        return bookDto;
    }
}
