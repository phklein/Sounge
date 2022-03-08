package soungegroup.soungeapi.domain.service.users;

import soungegroup.soungeapi.domain.model.users.User;

import java.util.List;

public interface UserService {
    List<User> findUserByEmailAndPasswordHash(String email, String passwordHash);
}
