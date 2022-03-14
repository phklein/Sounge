package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.repository.RoleRepository;
import soungegroup.soungeapi.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
}
