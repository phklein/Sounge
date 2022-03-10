package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.MemberRepository;
import soungegroup.soungeapi.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;
}
