package soungegroup.soungeapi.domain.service.users.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.domain.model.users.User;
import soungegroup.soungeapi.domain.repository.users.UserRepository;
import soungegroup.soungeapi.domain.service.users.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findUserByEmailAndPasswordHash(String email, String passwordHash) {
        return userRepository.findUserByLoginAndPasswordHash(email, passwordHash);
    }
}
