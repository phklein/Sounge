package soungegroup.soungeapi.domain.service.tags.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.mapper.GenreMapper;
import soungegroup.soungeapi.domain.repository.tags.GenreRepository;
import soungegroup.soungeapi.domain.service.tags.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreMapper mapper;
    private final GenreRepository repository;
}
