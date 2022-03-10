package soungegroup.soungeapi.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveRequestGenreDTO {
    private GenreName name;
}
