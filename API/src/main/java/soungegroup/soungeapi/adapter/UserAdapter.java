package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Role;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.RoleRepository;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter {
    private final ModelMapper mapper;

    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;

    public User toUser(UserSaveRequest userSaveRequest) {
        User user = mapper.map(userSaveRequest, User.class);

        List<Genre> genres = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        for (GenreName gn : userSaveRequest.getLikedGenres()) {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        }

        for (RoleName rn : userSaveRequest.getRoles()) {
            Optional<Role> role = roleRepository.findByName(rn);
            role.ifPresent(roles::add);
        }

        if (genres.size() < userSaveRequest.getLikedGenres().size() ||
                roles.size() < userSaveRequest.getRoles().size()) {
            return null;
        }

        user.setLikedGenres(genres);
        user.setRoles(roles);

        return user;
    }

    public UserLoginResponse toLoginResponse(User user) {
        return mapper.map(user, UserLoginResponse.class);
    }
}
