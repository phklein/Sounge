package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.UpdateUserProfileRequest;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserProfileResponse;
import soungegroup.soungeapi.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserLoginResponse> saveAndLogin(@RequestBody @Valid UserSaveRequest body) {
        return service.saveAndLogin(body);
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        return service.export();
    }

    @PostMapping("/auth")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest body) {
        return service.login(body);
    }

    @DeleteMapping("/{id}/auth")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @PostMapping("/{id}/likePost/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long id,
                                         @PathVariable Long postId) {
        return service.likePost(id, postId);
    }

    @DeleteMapping("/{id}/likePost/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id,
                                           @PathVariable Long postId) {
        return service.unlikePost(id, postId);
    }

    @PostMapping("/{id}/likeUser/{likedId}")
    public ResponseEntity<Void> likeUser(@PathVariable Long id,
                                         @PathVariable Long likedId) {
        return service.likeUser(id, likedId);
    }

    @DeleteMapping("/{id}/likeUser/{likedId}")
    public ResponseEntity<Void> unlikeUser(@PathVariable Long id,
                                           @PathVariable Long likedId) {
        return service.unlikeUser(id, likedId);
    }

    @PostMapping("/{id}/group/{groupId}")
    public ResponseEntity<Void> joinGroup(@PathVariable Long id,
                                          @PathVariable Long groupId) {
        return service.joinGroup(id, groupId);
    }

    @DeleteMapping("/{id}/group")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long id) {
        return service.leaveGroup(id);
    }

    @PostMapping("/{id}/genres/{genreName}")
    public ResponseEntity<Void> addGenre(@PathVariable Long id,
                                         @PathVariable GenreName genreName) {
        return service.addGenre(id, genreName);
    }

    @DeleteMapping("/{id}/genres/{genreName}")
    public ResponseEntity<Void> removeGenre(@PathVariable Long id,
                                            @PathVariable GenreName genreName) {
        return service.removeGenre(id, genreName);
    }

    @PostMapping("/{id}/roles/{roleName}")
    public ResponseEntity<Void> addRole(@PathVariable Long id,
                                        @PathVariable RoleName roleName) {
        return service.addRole(id, roleName);
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
    @GetMapping("/{id}")
    public  ResponseEntity<UserProfileResponse> getProfileForId(@PathVariable Long id){
        return  service.getProfileForId(id);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Void> updateProfile(@PathVariable Long id,
                                               @RequestBody UpdateUserProfileRequest body){
        return service.updateProfilePage(id, body);
    }

}
