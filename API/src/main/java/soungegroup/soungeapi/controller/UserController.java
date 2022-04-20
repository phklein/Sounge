package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserLoginResponse> saveAndLogin(@RequestBody UserSaveRequest body) {
        return service.saveAndLogin(body);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest body) {
        return service.login(body);
    }

    @DeleteMapping("/auth/{id}")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }
}
