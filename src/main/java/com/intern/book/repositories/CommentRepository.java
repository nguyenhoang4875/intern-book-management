package com.intern.book.repositories;

import com.intern.book.models.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
