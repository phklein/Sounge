package soungegroup.soungeapi.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.mapper.UserMapper;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final List<User> activeUsers;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.activeUsers = new ArrayList<>();
    }

    @Override
    public ResponseEntity<LoginResponse> saveAndAuthenticate(UserSaveRequest body) {
        User user = userRepository.save(mapper.toUser(body));
        activeUsers.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toLoginResponse(user));
    }

    @Override
    public ResponseEntity<LoginResponse> authenticate(LoginRequest body) {
        List<User> users = userRepository.findUserByEmailAndPasswordHash(body.getEmail(), body.getPassword());
        if (users.size() == 1) {
            User user = users.get(0);
            activeUsers.add(user);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toLoginResponse(user));
        } else if (users.size() > 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> logout(Long id) {
        if (activeUsers.removeIf(u -> u.getId().equals(id))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
