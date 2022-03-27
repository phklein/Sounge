package soungegroup.soungeapi.dto.user;

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
public class SaveRequestArtist extends SaveRequest {
    private Gender gender;
    private List<SaveRequestRole> roles;
}
