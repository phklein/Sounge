package soungegroup.soungeapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.GenreName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreSaveRequest {
    private GenreName name;
}
