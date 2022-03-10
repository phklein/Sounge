package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.ScheduleRepository;
import soungegroup.soungeapi.domain.service.ScheduleService;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
}
