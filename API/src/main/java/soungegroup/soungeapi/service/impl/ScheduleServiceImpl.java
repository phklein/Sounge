package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.repository.ScheduleRepository;
import soungegroup.soungeapi.service.ScheduleService;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
}
