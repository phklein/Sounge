package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.GenreRepository;
import soungegroup.soungeapi.domain.repository.GroupRepository;
import soungegroup.soungeapi.domain.service.GenreService;
import soungegroup.soungeapi.domain.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
}
