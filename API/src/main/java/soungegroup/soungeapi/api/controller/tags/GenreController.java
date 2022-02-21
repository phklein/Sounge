package soungegroup.soungeapi.api.controller.tags;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soungegroup.soungeapi.domain.service.tags.impl.GenreServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
@Log4j2
public class GenreController {
    private final DateUtil dateUtil;
    private final GenreServiceImpl genreService;
}
