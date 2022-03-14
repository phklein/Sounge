package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.repository.PostRepository;
import soungegroup.soungeapi.service.PostService;

@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
}
