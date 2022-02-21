package soungegroup.soungeapi.domain.service.feed.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.domain.repository.feed.CommentRepository;
import soungegroup.soungeapi.domain.service.feed.CommentService;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
}
