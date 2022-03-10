package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.domain.repository.PostRepository;
import soungegroup.soungeapi.domain.service.PostService;

@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
}
