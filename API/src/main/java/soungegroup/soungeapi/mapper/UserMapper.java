package soungegroup.soungeapi.mapper;

import org.springframework.stereotype.Component;
import soungegroup.soungeapi.dto.user.LoginResponse;
import soungegroup.soungeapi.dto.user.LoginResponseArtist;
import soungegroup.soungeapi.dto.user.SaveRequestArtist;
import soungegroup.soungeapi.model.*;
import soungegroup.soungeapi.model.relations.ArtistHasRole;
import soungegroup.soungeapi.model.relations.UserLikesGenre;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toUser(SaveRequestArtist body) {
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

    public LoginResponse toLoginResponseDTO(User user) {
        switch (user.getUserType()) {
            case ARTIST:
                Artist artist = (Artist) user;
                return LoginResponseArtist.builder()
                        .id(artist.getId())
                        .email(artist.getEmail())
                        .name(artist.getName())
                        .birthDate(artist.getBirthDate())
                        .state(artist.getState())
                        .city(artist.getCity())
                        .latitude(artist.getLatitude())
                        .longitude(artist.getLongitude())
                        .gender(artist.getGender())
                        .build();
            case GROUP:
                Group group = (Group) user;
                return LoginResponseArtist.builder()
                        .id(group.getId())
                        .email(group.getEmail())
                        .name(group.getName())
                        .birthDate(group.getBirthDate())
                        .state(group.getState())
                        .city(group.getCity())
                        .latitude(group.getLatitude())
                        .longitude(group.getLongitude())
                        .build();
            case PLACE:
                Place place = (Place) user;
                return LoginResponseArtist.builder()
                        .id(place.getId())
                        .email(place.getEmail())
                        .name(place.getName())
                        .birthDate(place.getBirthDate())
                        .state(place.getState())
                        .city(place.getCity())
                        .latitude(place.getLatitude())
                        .longitude(place.getLongitude())
                        .build();
            default:
                return null;
        }
    }
}
