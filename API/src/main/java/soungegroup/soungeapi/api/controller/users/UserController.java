package soungegroup.soungeapi.api.controller.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soungegroup.soungeapi.domain.service.users.impl.UserServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {
    private final DateUtil dateUtil;
    private final UserServiceImpl userService;
}
