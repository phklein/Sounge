package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GroupAdapter {
    private final ModelMapper mapper;

    private final GenreRepository genreRepository;

    public Group toGroup(GroupSaveRequest groupSaveRequest) {
        Group group = mapper.map(groupSaveRequest, Group.class);

        List<Genre> genres = new ArrayList<>();

        for (GenreName gn : groupSaveRequest.getGenres()) {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        }

        if (genres.size() < groupSaveRequest.getGenres().size()) {
            return null;
        }

        group.setGenres(genres);

        return group;
    }

    public GroupSimpleResponse toSimpleResponse(Group group) {
        return mapper.map(group, GroupSimpleResponse.class);
    }

    public GroupPageResponse toPageResponse(Group group) {
        GroupPageResponse response = mapper.map(group, GroupPageResponse.class);
        response.setAge(Period.between(group.getCreationDate(), LocalDate.now()).getYears());
        return response;
    }
}
