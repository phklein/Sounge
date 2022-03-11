package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.dto.user.LoginResponseDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;

public interface UserService {
    ResponseEntity<LoginResponseDTO> saveAndAuthenticate(SaveRequestArtistDTO body);
    ResponseEntity<LoginResponseDTO> authenticate(LoginRequestDTO body);
    ResponseEntity<Void> logout(Long id);
}
