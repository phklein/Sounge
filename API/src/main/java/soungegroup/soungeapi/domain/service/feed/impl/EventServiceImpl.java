package soungegroup.soungeapi.domain.service.feed.impl;

import lombok.RequiredArgsConstructor;
import soungegroup.soungeapi.domain.repository.feed.EventRepository;
import soungegroup.soungeapi.domain.service.feed.EventService;

@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
}
