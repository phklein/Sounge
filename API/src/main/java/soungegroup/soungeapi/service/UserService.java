package soungegroup.soungeapi.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.*;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body);
    ResponseEntity<UserLoginResponse> login(UserLoginRequest body);
    void pushSession (UserLoginResponse user);
    ResponseEntity<Boolean> checkSession (Long id);
    ResponseEntity<Void> logoff(Long id);

    ResponseEntity<Void> likePost(Long id, Long postId);
    ResponseEntity<Void> unlikePost(Long id, Long postId);
    ResponseEntity<Void> likeComment(Long id, Long commentId);
    ResponseEntity<Void> unlikeComment(Long id, Long commentId);

    ResponseEntity<Void> likeUser(Long id, Long likedId);
    ResponseEntity<Void> unlikeUser(Long id, Long likedId);

    ResponseEntity<Void> joinGroup(Long id, Long groupId);
    ResponseEntity<Void> leaveGroup(Long id);

    ResponseEntity<Void> updateGenres(Long id, List<GenreName> toAdd, List<GenreName> toRemove);
    ResponseEntity<Void> updateRoles(Long id, List<RoleName> toAdd, List<RoleName> toRemove);

    ResponseEntity<Void> updateSignature(Long id, SignatureType signatureType);
    ResponseEntity<Void> updateLocation(Long id, UserLocationUpdateRequest body);

    ResponseEntity<Void> updateProfilePage(Long id, UserProfileUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);
    ResponseEntity<Void> updatePassword(Long id, UserPasswordUpdateRequest body);
    ResponseEntity<Void> delete(Long id, String password);

    ResponseEntity<UserProfileResponse> getProfileById(Long viewerId, Long id);
    ResponseEntity<Page<PostSimpleResponse>> getPostsById(Long viewerId, Long id, Integer page);
    ResponseEntity<Page<UserContactResponse>> findContactList(Long id, Integer page);
    ResponseEntity<Page<NotificationSimpleResponse>> findNotifications(Long id, Integer Page);
    ResponseEntity<List<NotificationSimpleResponse>> checkNewMatches(Long id);
    ResponseEntity<Page<UserMatchResponse>> findMatchList(Long id,
                                                          Integer maxDistance,
                                                          Optional<Integer> minAge,
                                                          Optional<Integer> maxAge,
                                                          Optional<GenreName> genreName,
                                                          Optional<RoleName> roleName,
                                                          Optional<Sex> sex,
                                                          Optional<SkillLevel> skillLevel,
                                                          Integer page);
    ResponseEntity<Page<UserSimpleResponse>> findByName(String nameLike);
}
