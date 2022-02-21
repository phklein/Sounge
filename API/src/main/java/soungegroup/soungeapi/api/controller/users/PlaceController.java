package soungegroup.soungeapi.api.controller.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.domain.service.users.impl.PlaceServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/places")
@Log4j2
public class PlaceController {
    private final DateUtil dateUtil;
    private final PlaceServiceImpl placeService;
}
