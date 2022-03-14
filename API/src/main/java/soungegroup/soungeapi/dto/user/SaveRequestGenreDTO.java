package soungegroup.soungeapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.GenreName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveRequestGenreDTO {
    private GenreName name;
}
