package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.repository.CommentRepository;
import soungegroup.soungeapi.service.CommentService;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
}
