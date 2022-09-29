package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;

import java.util.List;

@Data
public class GenreUpdateRequest {
    private List<GenreName> toAdd;
    private List<GenreName> toRemove;
}
