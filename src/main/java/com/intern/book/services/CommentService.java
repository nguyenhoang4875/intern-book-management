package com.intern.book.services;

import com.intern.book.models.dto.CommentDto;

import java.util.Set;

public interface CommentService {

    Set<CommentDto> getAllCommentsByBook(Integer postId);

    CommentDto save(Integer bookId, CommentDto commentDto);

    void deleteCommentById(Integer commentId);

    CommentDto update(CommentDto commentDto);
}
