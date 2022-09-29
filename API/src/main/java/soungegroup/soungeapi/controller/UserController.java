package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.*;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.*;
import soungegroup.soungeapi.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<Page<UserSimpleResponse>> findByName(@RequestParam String nameLike) {
        return service.findByName(nameLike);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getProfileById(@RequestParam Long viewerId,
                                                              @PathVariable Long id) {
        return service.getProfileById(viewerId, id);
    }

    @GetMapping("{id}/match")
    public ResponseEntity<Page<UserMatchResponse>> findMatchList(@PathVariable Long id,
                                                                 @RequestParam Integer maxDistance,
                                                                 @RequestParam Optional<Integer> minAge,
                                                                 @RequestParam Optional<Integer> maxAge,
                                                                 @RequestParam Optional<GenreName> genreName,
                                                                 @RequestParam Optional<RoleName> roleName,
                                                                 @RequestParam Optional<Sex> sex,
                                                                 @RequestParam Optional<SkillLevel> skillLevel,
                                                                 @RequestParam Integer page) {
        return service.findMatchList(id, maxDistance, minAge, maxAge, genreName, roleName, sex, skillLevel, page);
    }

    @GetMapping("{id}/contacts")
    public ResponseEntity<Page<UserContactResponse>> findContactList(@PathVariable Long id,
                                                                     @RequestParam Integer page) {
        return service.findContactList(id, page);
    }

    @GetMapping("{id}/newMatches")
    public ResponseEntity<List<NotificationSimpleResponse>> checkNewMatches(@PathVariable Long id) {
        return service.checkNewMatches(id);
    }

    @GetMapping("{id}/notifications")
    public ResponseEntity<Page<NotificationSimpleResponse>> findNotifications(@PathVariable Long id,
                                                                              @PathVariable Integer page) {
        return service.findNotifications(id, page);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest body) {
        return service.login(body);
    }

    @GetMapping("/{id}/auth")
    public ResponseEntity<Boolean> checkSession(@PathVariable Long id) {
        return service.checkSession(id);
    }

    @DeleteMapping("/{id}/auth")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(@PathVariable Long id,
                                              @RequestBody @Valid UserProfileUpdateRequest body) {
        return service.updateProfilePage(id, body);
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<Void> updatePicture(@PathVariable Long id,
                                              @RequestBody @Valid PictureUpdateRequest body) {
        return service.updatePicture(id, body);
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable Long id,
                                               @RequestBody @Valid UserLocationUpdateRequest body) {
        return service.updateLocation(id, body);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody @Valid UserPasswordUpdateRequest body) {
        return service.updatePassword(id, body);
    }

    @PatchMapping("/{id}/signature")
    public ResponseEntity<Void> updateSignature(@PathVariable Long id,
                                                @RequestParam SignatureType signatureType) {
        return service.updateSignature(id, signatureType);
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

    @PostMapping("/{id}/likeComment/{postId}")
    public ResponseEntity<Void> likeComment(@PathVariable Long id,
                                            @PathVariable Long commentId) {
        return service.likeComment(id, commentId);
    }

    @DeleteMapping("/{id}/likeComment/{postId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long id,
                                              @PathVariable Long commentId) {
        return service.unlikeComment(id, commentId);
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

    @PutMapping("/{id}/genres/multiple")
    public ResponseEntity<Void> updateGenres(@PathVariable Long id,
                                             @RequestBody GenreUpdateRequest body) {
        return service.updateGenres(id, body.getToAdd(), body.getToRemove());
    }

    @PutMapping("/{id}/roles/multiple")
    public ResponseEntity<Void> updateRoles(@PathVariable Long id,
                                            @RequestBody RoleUpdateRequest body) {
        return service.updateRoles(id, body.getToAdd(), body.getToRemove());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam String pwd) {
        return service.delete(id, pwd);
    }
}
