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
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;

import java.util.ArrayList;
import java.util.List;

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
            genres.add(genreRepository.findByName(gn));
        }

        for (RoleName rn : userSaveRequest.getRoles()) {
            roles.add(roleRepository.findByName(rn));
        }

        user.setLikedGenres(genres);
        user.setRoles(roles);

        return user;
    }

    public UserLoginResponse toLoginResponse(User user) {
        return mapper.map(user, UserLoginResponse.class);
    }
}
