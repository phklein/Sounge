package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.dto.user.LoginResponseArtistDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;

public interface UserService {
    ResponseEntity<LoginResponseArtistDTO> saveAndAuthenticate(SaveRequestArtistDTO body);
    ResponseEntity<LoginResponseArtistDTO> authenticate(LoginRequestDTO body);
    ResponseEntity<Void> logout(Long id);
}
