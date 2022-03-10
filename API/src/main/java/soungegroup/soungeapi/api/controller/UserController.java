package soungegroup.soungeapi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.api.dto.user.LoginRequestDTO;
import soungegroup.soungeapi.api.dto.user.LoginResponseDTO;
import soungegroup.soungeapi.api.dto.user.SaveRequestArtistDTO;
import soungegroup.soungeapi.domain.service.impl.UserServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {
    private final DateUtil dateUtil;
    private final UserServiceImpl service;

    // Registering
    @PostMapping("/artists")
    public ResponseEntity<LoginResponseDTO> saveArtistAndLogin(@RequestBody SaveRequestArtistDTO body) {
        logDateTime();
        return service.saveAndAuthenticate(body);
    }

    // Authentication
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        logDateTime();
        return service.authenticate(body);
    }

    @DeleteMapping("/login/{id}")
    public ResponseEntity<Void> logout(@PathVariable Long id) {
        logDateTime();
        return service.logout(id);
    }

    private void logDateTime() {
        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
    }

}
