package com.intern.book.services.servicesIplm;

import com.intern.book.converter.bases.Converter;
import com.intern.book.exeptions.NotFoundException;
import com.intern.book.models.dao.Book;
import com.intern.book.models.dao.Comment;
import com.intern.book.models.dto.CommentDto;
import com.intern.book.repositories.BookRepository;
import com.intern.book.repositories.CommentRepository;
import com.intern.book.services.CommentService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private Converter<Comment, CommentDto> commentDaoToCommentDtoConverter;

    @Autowired
    private Converter<CommentDto, Comment> commentDtoToCommentDaoConverter;

    @Override
    @Transactional
    public Set<CommentDto> getAllCommentsByPost(Integer bookId) {
        Set<CommentDto> comments = commentDaoToCommentDtoConverter.convert(bookRepository.findById(bookId).get().getComments());
        comments = comments.stream().sorted(Comparator.comparing(CommentDto::getCreatedAt).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
        return comments;
    }

    @Override
    @Transactional
    public CommentDto save(Integer id, CommentDto commentDto) {
        Book book = bookRepository.findById(id).get();
        Comment comment = commentDtoToCommentDaoConverter.convert(commentDto);
        comment.setCreatedAt(LocalDateTime.now());
        book.getComments().add(comment);
        comment.setUser(userService.getCurrentUser());
        commentRepository.save(comment);
        if (commentDto.getId() == 0) {
            commentDto.setId(comment.getId());
        }
        return commentDaoToCommentDtoConverter.convert(comment);
    }

    @Override
    public void deleteCommentById(Integer postId, Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
        } else {
            throw new NotFoundException("comment not found with id: " + commentId);
        }
    }

}
