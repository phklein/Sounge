package soungegroup.soungeapi.mapper;

import org.springframework.stereotype.Component;
import soungegroup.soungeapi.dto.user.LoginResponseArtistDTO;
import soungegroup.soungeapi.dto.user.SaveRequestArtistDTO;
import soungegroup.soungeapi.model.*;
import soungegroup.soungeapi.model.relations.ArtistHasRole;
import soungegroup.soungeapi.model.relations.UserLikesGenre;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toUser(SaveRequestArtistDTO body) {
        Artist artist = Artist.builder()
                .email(body.getEmail())
                .passwordHash(body.getPassword())
                .name(body.getName())
                .description(body.getDescription())
                .birthDate(body.getBirthDate())
                .state(body.getState())
                .city(body.getCity())
                .gender(body.getGender())
                .build();

        List<UserLikesGenre> likedGenresAssoc = new ArrayList<>();
        List<ArtistHasRole> rolesAssoc = new ArrayList<>();

        body.getLikedGenres().forEach(g ->
                likedGenresAssoc.add(UserLikesGenre.builder()
                        .user(artist)
                        .genre(Genre.builder().name(g.getName()).build())
                        .build())
        );

        body.getRoles().forEach(r ->
                rolesAssoc.add(ArtistHasRole.builder()
                        .artist(artist)
                        .role(Role.builder().name(r.getName()).build())
                        .startDate(r.getStartDate())
                        .build())
        );

        artist.setLikedGenresAssoc(likedGenresAssoc);
        artist.setRolesAssoc(rolesAssoc);

        return artist;
    }

    public LoginResponseArtistDTO toLoginResponseDTO(User user) {
        return LoginResponseArtistDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .state(user.getState())
                .city(user.getCity())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .build();
    }
}
