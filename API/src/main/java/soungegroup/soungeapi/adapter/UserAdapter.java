package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Role;
import soungegroup.soungeapi.model.Signature;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.RoleRepository;
import soungegroup.soungeapi.repository.SignatureRepository;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.util.Mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter {
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final SignatureRepository signatureRepository;

    public User toUser(UserSaveRequest userSaveRequest) {
        User user = Mapper.INSTANCE.map(userSaveRequest, User.class);

        List<Genre> genres = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        userSaveRequest.getLikedGenres().forEach(gn -> {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        });

        userSaveRequest.getRoles().forEach(rn -> {
            Optional<Role> role = roleRepository.findByName(rn);
            role.ifPresent(roles::add);
        });

        if (genres.size() < userSaveRequest.getLikedGenres().size() ||
                roles.size() < userSaveRequest.getRoles().size()) {
            return null;
        }

        Signature signature = new Signature();
        signature.setExpiryDateTime(LocalDateTime.now());

        user.setLikedGenres(genres);
        user.setRoles(roles);
        user.setSignature(signatureRepository.save(signature));

        return user;
    }

    public UserLoginResponse toLoginResponse(User user) {
        return Mapper.INSTANCE.map(user, UserLoginResponse.class);
    }
}
