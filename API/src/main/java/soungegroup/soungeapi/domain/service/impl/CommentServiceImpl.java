package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.domain.repository.CommentRepository;
import soungegroup.soungeapi.domain.service.CommentService;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
}
