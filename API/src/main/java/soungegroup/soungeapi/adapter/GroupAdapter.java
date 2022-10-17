package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.util.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GroupAdapter {
    private final GenreRepository genreRepository;

    public Group toGroup(GroupSaveRequest groupSaveRequest) {
        Group group = Mapper.INSTANCE.map(groupSaveRequest, Group.class);

        List<Genre> genres = new ArrayList<>();

        groupSaveRequest.getGenres().forEach(gn -> {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        });

        if (genres.size() < groupSaveRequest.getGenres().size()) {
            return null;
        }

        group.setGenres(genres);

        return group;
    }
}
