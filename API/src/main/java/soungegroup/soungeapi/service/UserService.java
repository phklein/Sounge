package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.LoginResponse;

public interface UserService {
    ResponseEntity<LoginResponse> saveAndAuthenticate(UserSaveRequest body);
    ResponseEntity<LoginResponse> authenticate(LoginRequest body);
    ResponseEntity<Void> logout(Long id);
}
