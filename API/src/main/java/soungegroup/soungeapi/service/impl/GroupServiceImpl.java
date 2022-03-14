package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
}
