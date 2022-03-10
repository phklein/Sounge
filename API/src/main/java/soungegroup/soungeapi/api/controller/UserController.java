package soungegroup.soungeapi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.api.dto.LoginRequestDTO;
import soungegroup.soungeapi.api.dto.LoginResponseDTO;
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

    // Login
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return service.authenticate(body.getEmail(), body.getPassword());
    }

    @DeleteMapping("/login/{id}")
    public ResponseEntity<Void> logout(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return service.logout(id);
    }

}
