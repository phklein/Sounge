package soungegroup.soungeapi.domain.service.users.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.mapper.PlaceMapper;
import soungegroup.soungeapi.domain.repository.users.PlaceRepository;
import soungegroup.soungeapi.domain.service.users.PlaceService;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceMapper mapper;
    private final PlaceRepository repository;
}
