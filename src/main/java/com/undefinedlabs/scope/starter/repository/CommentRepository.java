package com.undefinedlabs.scope.starter.repository;

import com.undefinedlabs.scope.starter.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
