package soungegroup.soungeapi.domain.service.tags.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.tags.ScheduleRepository;
import soungegroup.soungeapi.domain.service.tags.ScheduleService;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
}
