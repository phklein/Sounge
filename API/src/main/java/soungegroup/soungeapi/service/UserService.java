package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserProfileResponse;

public interface UserService {
    ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body);
    ResponseEntity<UserLoginResponse> login(UserLoginRequest body);
    ResponseEntity<Void> logoff(Long id);

    ResponseEntity<Void> likePost(Long id, Long postId);
    ResponseEntity<Void> unlikePost(Long id, Long postId);

    ResponseEntity<Void> likeUser(Long id, Long likedId);
    ResponseEntity<Void> unlikeUser(Long id, Long likedId);

    ResponseEntity<Void> joinGroup(Long id, Long groupId);
    ResponseEntity<Void> leaveGroup(Long id);

    ResponseEntity<Void> addGenre(Long id, GenreName genreName);
    ResponseEntity<Void> addRole(Long id, RoleName roleName);
    ResponseEntity<Void> removeGenre(Long id, GenreName genreName);
    ResponseEntity<Void> removeRole(Long id, RoleName roleName);

    ResponseEntity<Void> updateProfilePage(Long id, UserProfileUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);
    ResponseEntity<Void> updatePassword(Long id, UserPasswordUpdateRequest body);
    ResponseEntity<Void> delete(Long id, String password);

    ResponseEntity<String> export();
    Boolean hasSession (User user);
    ResponseEntity<UserProfileResponse> getProfileById(Long id);
}
