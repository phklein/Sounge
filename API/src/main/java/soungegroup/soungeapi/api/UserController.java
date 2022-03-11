package soungegroup.soungeapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.dto.user.LoginResponseUserDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;

@RequestMapping(UserController.URL)
public interface UserController {
    String URL = "/users";

    @PostMapping("/artists")
    ResponseEntity<LoginResponseUserDTO> saveArtistAndLogin(@RequestBody SaveRequestArtistDTO body);

    @PostMapping("/login")
    ResponseEntity<LoginResponseUserDTO> login(@RequestBody LoginRequestDTO body);

    @DeleteMapping("/login/{id}")
    ResponseEntity<Void> logout(@PathVariable Long id);
}
