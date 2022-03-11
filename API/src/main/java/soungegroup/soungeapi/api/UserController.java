package soungegroup.soungeapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.dto.user.LoginResponseArtistDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;

@RequestMapping(UserController.URL)
public interface UserController {
    String URL = "/users";

    @PostMapping("/artists")
    ResponseEntity<LoginResponseArtistDTO> saveArtistAndLogin(@RequestBody SaveRequestArtistDTO body);

    @PostMapping("/login")
    ResponseEntity<LoginResponseArtistDTO> login(@RequestBody LoginRequestDTO body);

    @DeleteMapping("/login/{id}")
    ResponseEntity<Void> logout(@PathVariable Long id);
}
