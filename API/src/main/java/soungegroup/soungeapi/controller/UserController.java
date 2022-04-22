package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.PasswordChangeRequest;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserProfileResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;
import soungegroup.soungeapi.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserSimpleResponse>> findAll() {
        return service.findAll();
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        return service.export();
    }

    @PostMapping
    public ResponseEntity<UserLoginResponse> saveAndLogin(@RequestBody @Valid UserSaveRequest body) {
        return service.saveAndLogin(body);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest body) {
        return service.login(body);
    }

    @PostMapping("/{id}/group/{groupId}")
    public ResponseEntity<Void> joinGroup(@PathVariable Long id,
                                          @PathVariable Long groupId) {
        return service.joinGroup(id, groupId);
    }

    @PostMapping("/{id}/genres/{genreName}")
    public ResponseEntity<Void> addGenre(@PathVariable Long id,
                                         @PathVariable GenreName genreName) {
        return service.addGenre(id, genreName);
    }

    @PostMapping("/{id}/roles/{roleName}")
    public ResponseEntity<Void> addRole(@PathVariable Long id,
                                        @PathVariable RoleName roleName) {
        return service.addRole(id, roleName);
    }

    @DeleteMapping("/{id}/group/{groupId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long id,
                                           @PathVariable Long groupId) {
        return service.leaveGroup(id, groupId);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody @Valid PasswordChangeRequest body) {
        return service.changePassword(id, body);
    }

    @DeleteMapping("/{id}/auth")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @DeleteMapping("/{id}/genres/{genreName}")
    public ResponseEntity<Void> removeGenre(@PathVariable Long id,
                                            @PathVariable GenreName genreName) {
        return service.removeGenre(id, genreName);
    }

    @DeleteMapping("/{id}/roles/{roleName}")
    public ResponseEntity<Void> removeRole(@PathVariable Long id,
                                           @PathVariable RoleName roleName) {
        return service.removeRole(id, roleName);
    }

    @DeleteMapping("/{id}/{pwd}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @PathVariable String pwd) {
        return service.delete(id, pwd);
    }
    @GetMapping("/profile/{id}")
    public  ResponseEntity<UserProfileResponse> getProfileForId(@PathVariable Long id){
        return  service.getProfileForId(id);
    }

}
