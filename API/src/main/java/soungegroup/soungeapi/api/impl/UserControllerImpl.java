package soungegroup.soungeapi.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.api.UserController;
import soungegroup.soungeapi.dto.user.LoginRequest;
import soungegroup.soungeapi.dto.user.LoginResponse;
import soungegroup.soungeapi.dto.user.SaveRequestArtist;
import soungegroup.soungeapi.service.impl.UserServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Log4j2
public class UserControllerImpl implements UserController {
    private final DateUtil dateUtil;
    private final UserServiceImpl service;

    // Registering
    @Override
    public ResponseEntity<LoginResponse> saveArtistAndLogin(@RequestBody SaveRequestArtist body) {
        logDateTime();
        return service.saveAndAuthenticate(body);
    }

    // Authentication
    @Override
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body) {
        logDateTime();
        return service.authenticate(body);
    }

    @Override
    public ResponseEntity<Void> logout(@PathVariable Long id) {
        logDateTime();
        return service.logout(id);
    }

    private void logDateTime() {
        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
    }

}
