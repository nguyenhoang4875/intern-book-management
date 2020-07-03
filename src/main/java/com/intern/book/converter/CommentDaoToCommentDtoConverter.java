package com.intern.book.converter;

import com.intern.book.converter.bases.Converter;
import com.intern.book.models.dao.Comment;
import com.intern.book.models.dto.CommentDto;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDaoToCommentDtoConverter extends Converter<Comment, CommentDto> {

    @Autowired
    private UserService userService;

    @Override
    public CommentDto convert(Comment source) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(source.getId());
        commentDto.setMessage(source.getMessage());
        commentDto.setCreatedAt(source.getCreatedAt());
        commentDto.setUpdatedAt(source.getUpdatedAt());
        commentDto.setUsername(source.getUser().getUsername());
        commentDto.setAvatarUrl(userService.getCurrentUser().getAvatar());
        return commentDto;
    }
}
