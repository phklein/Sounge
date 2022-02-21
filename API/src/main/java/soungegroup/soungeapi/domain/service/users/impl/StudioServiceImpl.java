package soungegroup.soungeapi.domain.service.users.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.users.StudioRepository;
import soungegroup.soungeapi.domain.service.users.StudioService;

@Service
@RequiredArgsConstructor
public class StudioServiceImpl implements StudioService {
    private final StudioRepository repository;
}
