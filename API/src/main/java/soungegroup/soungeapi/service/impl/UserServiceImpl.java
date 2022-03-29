package soungegroup.soungeapi.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.mapper.UserMapper;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.response.LoginResponse;
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
    public ResponseEntity<LoginResponse> saveAndAuthenticate(ArtistSaveRequest body) {
        User user = userRepository.save(mapper.toUser(body));
        activeUsers.add(user);
        return new ResponseEntity<>(mapper.toLoginResponse(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponse> authenticate(LoginRequest body) {
        List<User> users = userRepository.findUserByEmailAndPasswordHash(body.getEmail(), body.getPassword());
        if (users.size() == 1) {
            User user = users.get(0);
            activeUsers.add(user);
            return new ResponseEntity<>(mapper.toLoginResponse(user), HttpStatus.OK);
        } else if (users.size() > 1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> logout(Long id) {
        if (activeUsers.removeIf(u -> u.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
