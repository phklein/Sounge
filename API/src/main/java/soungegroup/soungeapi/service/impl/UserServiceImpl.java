package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.UserAdapter;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserAdapter adapter;
    private final List<UserLoginResponse> sessions;

    @Override
    public ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body) {
        User savedUser = repository.save(adapter.toUser(body));
        UserLoginResponse loginResponse = adapter.toLoginResponse(savedUser);

        sessions.add(loginResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest body) {
        List<UserLoginResponse> foundUsers = repository.findUserByEmailAndPassword(body.getEmail(), body.getPassword());

        if (foundUsers.size() == 1) {
            UserLoginResponse user = foundUsers.get(0);
            sessions.add(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else if (foundUsers.size() > 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> logoff(Long id) {
        if (sessions.removeIf(u -> u.getId().equals(id))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
