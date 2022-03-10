package soungegroup.soungeapi.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.api.dto.LoginResponseDTO;
import soungegroup.soungeapi.domain.mapper.UserMapper;
import soungegroup.soungeapi.domain.model.User;
import soungegroup.soungeapi.domain.repository.UserRepository;
import soungegroup.soungeapi.domain.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final List<User> activeUsers;

    @Override
    public ResponseEntity<LoginResponseDTO> authenticate(String email, String password) {
        List<User> users = repository.findUserByLoginAndPasswordHash(email, password);
        if (users.size() == 1) {
            User user = users.get(0);
            activeUsers.add(user);
            return new ResponseEntity<>(mapper.toLoginResponseDTO(user), HttpStatus.OK);
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
