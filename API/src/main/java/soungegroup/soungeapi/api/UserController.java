package soungegroup.soungeapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.dto.user.LoginRequest;
import soungegroup.soungeapi.dto.user.LoginResponse;
import soungegroup.soungeapi.dto.user.ArtistSaveRequest;

@RequestMapping(UserController.URL)
public interface UserController {
    String URL = "/users";

    @PostMapping("/artists")
    ResponseEntity<LoginResponse> saveArtistAndLogin(@RequestBody ArtistSaveRequest body);

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body);

    @DeleteMapping("/login/{id}")
    ResponseEntity<Void> logout(@PathVariable Long id);
}
