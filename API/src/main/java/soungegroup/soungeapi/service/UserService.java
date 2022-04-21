package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.PasswordChangeRequest;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserPageResponse;

public interface UserService {
    ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body);
    ResponseEntity<UserLoginResponse> login(UserLoginRequest body);
    ResponseEntity<Void> logoff(Long id);

    ResponseEntity<Void> likePost(Long id, Long postId);
    ResponseEntity<Void> unlikePost(Long id, Long postId);

    ResponseEntity<Void> joinGroup(Long id, Long groupId);
    ResponseEntity<Void> leaveGroup(Long id);

    ResponseEntity<Void> addGenre(Long id, GenreName genreName);
    ResponseEntity<Void> addRole(Long id, RoleName roleName);
    ResponseEntity<Void> removeGenre(Long id, GenreName genreName);
    ResponseEntity<Void> removeRole(Long id, RoleName roleName);

    ResponseEntity<Void> changePassword(Long id, PasswordChangeRequest body);

    ResponseEntity<Void> delete(Long id, String password);

    ResponseEntity<UserPageResponse> findById(Long id);
    ResponseEntity export();
}
