package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.GroupRequest;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.LoginResponse;
public interface GroupService {
    // ResponseEntity<LoginResponse> saveAndAuthenticate(UserSaveRequest body);
    // ResponseEntity<LoginResponse> authenticate(LoginRequest body);
    // ResponseEntity<Void> logout(Long id);


=======
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupService {
    ResponseEntity<GroupSimpleResponse> save(GroupSaveRequest body);

    ResponseEntity<List<GroupSimpleResponse>> findAll();
    ResponseEntity export();

    ResponseEntity<Void> delete(Long id);
>>>>>>> develop
}
