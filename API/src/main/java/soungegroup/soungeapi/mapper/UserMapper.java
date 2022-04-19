package soungegroup.soungeapi.mapper;

import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.*;
import soungegroup.soungeapi.model.relations.ArtistHasRole;
import soungegroup.soungeapi.model.relations.UserLikesGenre;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.GroupRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.ArtistLoginResponse;
import soungegroup.soungeapi.response.GroupLoginResponse;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.response.PlaceLoginResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toUser(UserSaveRequest body) {
        if (body instanceof ArtistSaveRequest) {
            ArtistSaveRequest wrapper = (ArtistSaveRequest) body;
            Artist artist = Artist.builder()
                    .email(wrapper.getEmail())
                    .passwordHash(wrapper.getPassword())
                    .name(wrapper.getName())
                    .description(wrapper.getDescription())
                    .birthDate(wrapper.getBirthDate())
                    .state(wrapper.getState())
                    .city(wrapper.getCity())
                    .gender(wrapper.getGender())
                    .build();

            List<UserLikesGenre> likedGenresAssoc = new ArrayList<>();
            List<ArtistHasRole> rolesAssoc = new ArrayList<>();


            wrapper.getLikedGenres().forEach(g ->
                    likedGenresAssoc.add(UserLikesGenre.builder()
                            .user(artist)
                            .genre(Genre.builder().name(g.getName()).build())
                            .build())
            );

            wrapper.getRoles().forEach(r ->
                    rolesAssoc.add(ArtistHasRole.builder()
                            .artist(artist)
                            .role(Role.builder().name(r.getName()).build())
                            .build())
            );

            artist.setLikedGenresAssoc(likedGenresAssoc);
            artist.setRolesAssoc(rolesAssoc);

            return artist;
        }
        if (body instanceof GroupRequest){
                GroupRequest wrapper = (GroupRequest) body;
                Group group = Group.builder()
                        .email(wrapper.getEmail())
                        .passwordHash(wrapper.getPassword())
                        .name(wrapper.getName())
                        .description(wrapper.getDescription())
                        .birthDate(wrapper.getBirthDate())
                        .state(wrapper.getState())
                        .city(wrapper.getCity())
                        .build();

                List<Artist> members = new ArrayList<>();
                wrapper.getMembers().stream().forEachOrdered(artist ->
                        members.add(artist)
                        );
                group.setMembers(members);


                return group;
        }
        PlaceRe wrapper = (GroupRequest) body;
        Group group = Group.builder()
                .email(wrapper.getEmail())
                .passwordHash(wrapper.getPassword())
                .name(wrapper.getName())
                .description(wrapper.getDescription())
                .birthDate(wrapper.getBirthDate())
                .state(wrapper.getState())
                .city(wrapper.getCity())
                .build();

        List<Artist> members = new ArrayList<>();
        wrapper.getMembers().stream().forEachOrdered(artist ->
                members.add(artist)
        );
        group.setMembers(members);


        return group;
    }

    public LoginResponse toLoginResponse(User user) {
        switch (user.getUserType()) {
            case ARTIST:
                Artist artist = (Artist) user;
                return ArtistLoginResponse.builder()
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
                return GroupLoginResponse.builder()
                        .id(group.getId())
                        .email(group.getEmail())
                        .name(group.getName())
                        .birthDate(group.getBirthDate())
                        .state(group.getState())
                        .city(group.getCity())
                        .latitude(group.getLatitude())
                        .longitude(group.getLongitude()).build();
            case PLACE:
                Place place = (Place) user;
                return PlaceLoginResponse.builder()
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
