package soungegroup.soungeapi.domain.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.api.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.api.dto.user.LoginResponseDTO;
import soungegroup.soungeapi.api.dto.user.SaveRequestArtistDTO;
import soungegroup.soungeapi.domain.mapper.UserMapper;
import soungegroup.soungeapi.domain.model.User;
import soungegroup.soungeapi.domain.repository.UserRepository;
import soungegroup.soungeapi.domain.service.UserService;

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
    public ResponseEntity<LoginResponseDTO> saveAndAuthenticate(SaveRequestArtistDTO body) {
        User user = userRepository.save(mapper.toUser(body));
        activeUsers.add(user);
        return new ResponseEntity<>(mapper.toLoginResponseDTO(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponseDTO> authenticate(LoginRequestDTO body) {
        List<User> users = userRepository.findUserByEmailAndPasswordHash(body.getEmail(), body.getPassword());
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
