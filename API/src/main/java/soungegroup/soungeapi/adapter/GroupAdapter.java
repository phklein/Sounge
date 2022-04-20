package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GroupAdapter {
    private final ModelMapper mapper;

    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public Group toGroup(GroupSaveRequest groupSaveRequest) {
        Group group = mapper.map(groupSaveRequest, Group.class);

        List<Genre> genres = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (GenreName gn : groupSaveRequest.getGenres()) {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        }

        for (Long id : groupSaveRequest.getUsersIds()) {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(users::add);
        }

        if (users.size() < groupSaveRequest.getUsersIds().size() ||
                genres.size() < groupSaveRequest.getGenres().size()) {
            return null;
        }

        group.setGenres(genres);
        group.setUsers(users);

        return group;
    }

    public GroupSimpleResponse toSimpleResponse(Group group) {
        return mapper.map(group, GroupSimpleResponse.class);
    }

    public List<GroupSimpleResponse> toSimpleResponse(List<Group> groups) {
        return mapper.map(groups, new TypeToken<List<GroupSimpleResponse>>() {}.getType());
    }
}
