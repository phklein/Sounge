package soungegroup.soungeapi.domain.service.users.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.repository.users.MemberRepository;
import soungegroup.soungeapi.domain.service.users.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;
}
