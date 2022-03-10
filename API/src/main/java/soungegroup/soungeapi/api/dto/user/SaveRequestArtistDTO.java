package soungegroup.soungeapi.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.Gender;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SaveRequestArtistDTO extends SaveRequestUserDTO {
    private Gender gender;
    private List<SaveRequestRoleDTO> roles;
}
