package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.request.GroupRequest;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final Mapper mapper;
    // @Override
    // public ResponseEntity<LoginResponse> saveAndAuthenticate(GroupRequest body) {
        
    //     return null;
    // }
    // @Override
    // public ResponseEntity<LoginResponse> authenticate(LoginRequest body) {

    //     return null;
    // }
    // @Override
    // public ResponseEntity<Void> logout(Long id) {
    //     return null;
    // }



    

}
