package soungegroup.soungeapi.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soungegroup.soungeapi.api.GroupController;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.service.impl.GroupServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Log4j2

public class GroupeControllerImpl implements GroupController {
    private final DateUtil dateUtil;
    private final GroupServiceImpl service;
    @Override
    public ResponseEntity<LoginResponse> saveBandAndLogin(ArtistSaveRequest body) {
        logDateTime();
        return null;
    }
    private void logDateTime() {
        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
    }
}
