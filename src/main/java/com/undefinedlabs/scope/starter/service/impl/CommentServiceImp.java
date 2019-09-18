package com.undefinedlabs.scope.starter.service.impl;

import com.undefinedlabs.scope.starter.model.Comment;
import com.undefinedlabs.scope.starter.repository.CommentRepository;
import com.undefinedlabs.scope.starter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }
}
