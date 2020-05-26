package com.intern.book.converter;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Comment;
import com.intern.book.models.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoToCommentDaoConverter extends Converter<CommentDto, Comment> {

    @Override
    public Comment convert(CommentDto source) {
        Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setMessage(source.getMessage());
        return comment;
    }
}
