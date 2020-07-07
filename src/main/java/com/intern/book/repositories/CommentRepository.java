package com.intern.book.repositories;

import com.intern.book.models.dao.Comment;
import com.intern.book.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByUser(User user);
}
