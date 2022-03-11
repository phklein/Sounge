package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.dto.user.LoginResponseArtistDTO;
import soungegroup.soungeapi.dto.user.LoginResponseUserDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;

public interface UserService {
    ResponseEntity<LoginResponseUserDTO> saveAndAuthenticate(SaveRequestArtistDTO body);
    ResponseEntity<LoginResponseUserDTO> authenticate(LoginRequestDTO body);
    ResponseEntity<Void> logout(Long id);
}
