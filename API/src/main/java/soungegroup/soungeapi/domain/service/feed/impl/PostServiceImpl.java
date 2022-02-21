package soungegroup.soungeapi.domain.service.feed.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.domain.repository.feed.PostRepository;
import soungegroup.soungeapi.domain.service.feed.PostService;

@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
}
