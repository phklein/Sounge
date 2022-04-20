package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.PasswordChangeRequest;
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

    @DeleteMapping("/{id}/auth")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody PasswordChangeRequest body) {
        return service.changePassword(id, body);
    }

    @DeleteMapping("/{id}/{pwd}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @PathVariable String pwd) {
        return service.delete(id, pwd);
    }
}
