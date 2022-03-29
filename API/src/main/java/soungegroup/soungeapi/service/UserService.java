package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.response.LoginResponse;

public interface UserService {
    ResponseEntity<LoginResponse> saveAndAuthenticate(ArtistSaveRequest body);
    ResponseEntity<LoginResponse> authenticate(LoginRequest body);
    ResponseEntity<Void> logout(Long id);
}
