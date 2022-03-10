package soungegroup.soungeapi.domain.mapper;

import org.springframework.stereotype.Component;
import soungegroup.soungeapi.api.dto.LoginResponseDTO;
import soungegroup.soungeapi.domain.model.User;

@Component
public class UserMapper {
    public LoginResponseDTO toLoginResponseDTO(User user) {
        return LoginResponseDTO.builder()
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
