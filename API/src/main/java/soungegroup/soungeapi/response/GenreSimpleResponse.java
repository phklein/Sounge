package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreSimpleResponse {
    private Long id;
    private GenreName name;
}
