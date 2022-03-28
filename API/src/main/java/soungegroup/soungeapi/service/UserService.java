package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.dto.user.LoginRequest;
import soungegroup.soungeapi.dto.user.LoginResponse;
import soungegroup.soungeapi.dto.user.ArtistSaveRequest;

public interface UserService {
    ResponseEntity<LoginResponse> saveAndAuthenticate(ArtistSaveRequest body);
    ResponseEntity<LoginResponse> authenticate(LoginRequest body);
    ResponseEntity<Void> logout(Long id);
}
