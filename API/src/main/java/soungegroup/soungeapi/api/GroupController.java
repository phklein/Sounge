package soungegroup.soungeapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.request.GroupRequest;

@RequestMapping(UserController.URL + GroupController.URl)
public interface GroupController {
     String URl = "/bands";
     @PostMapping()
     ResponseEntity<LoginResponse> saveBandAndLogin(@RequestBody GroupRequest body);

}
