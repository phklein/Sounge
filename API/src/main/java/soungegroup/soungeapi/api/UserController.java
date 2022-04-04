package soungegroup.soungeapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.GroupRequest;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.response.GroupLoginResponse;
import soungegroup.soungeapi.response.LoginResponse;

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
