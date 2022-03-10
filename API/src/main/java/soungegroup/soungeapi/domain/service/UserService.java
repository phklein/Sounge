package soungegroup.soungeapi.domain.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.api.dto.LoginResponseDTO;

public interface UserService {
    ResponseEntity<LoginResponseDTO> authenticate(String email, String password);
    ResponseEntity<Void> logout(Long id);
}
