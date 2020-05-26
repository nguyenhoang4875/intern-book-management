package com.intern.book.converter;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Comment;
import com.intern.book.models.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDaoToCommentDtoConverter extends Converter<Comment, CommentDto> {

    @Override
    public CommentDto convert(Comment source) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(source.getId());
        commentDto.setMessage(source.getMessage());
        commentDto.setCreatedAt(source.getCreatedAt());
        commentDto.setUpdatedAt(source.getUpdatedAt());
        commentDto.setUsername(commentDto.getUsername());
        return commentDto;
    }
}
