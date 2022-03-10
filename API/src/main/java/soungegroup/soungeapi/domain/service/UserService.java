package soungegroup.soungeapi.domain.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.api.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.api.dto.user.LoginResponseDTO;
import soungegroup.soungeapi.api.dto.user.SaveRequestArtistDTO;

public interface UserService {
    ResponseEntity<LoginResponseDTO> saveAndAuthenticate(SaveRequestArtistDTO body);
    ResponseEntity<LoginResponseDTO> authenticate(LoginRequestDTO body);
    ResponseEntity<Void> logout(Long id);
}
