package soungegroup.soungeapi.domain.service.tags.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.tags.RoleRepository;
import soungegroup.soungeapi.domain.service.tags.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
}
