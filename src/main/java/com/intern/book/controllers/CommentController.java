package com.intern.book.controllers;

import com.intern.book.models.dto.CommentDto;
import com.intern.book.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/comments/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{bookId}")
    public Set<CommentDto> getCommetsByBookId(@PathVariable Integer bookId) {
        return commentService.getAllCommentsByPost(bookId);
    }

    @PostMapping("/{bookId}")
    public CommentDto addCommentByBook(@PathVariable("bookId") Integer bookId, @RequestBody @Valid CommentDto commentDto) {
        commentDto.setId(0);
        return commentService.save(bookId, commentDto);
    }

    @PutMapping("/{commentId}")
    public CommentDto updateCommentByPost(@RequestBody @Valid CommentDto commentDto, @PathVariable Integer commentId) {
        commentDto.setId(commentId);
        return commentService.update(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Integer commentId) {
        commentService.deleteCommentById(commentId);
    }
}
