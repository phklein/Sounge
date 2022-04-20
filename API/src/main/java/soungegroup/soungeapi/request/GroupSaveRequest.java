package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupSaveRequest {
    private String name;
    private String description;
    private LocalDate creationDate;
    private List<GenreName> genres;
    private List<Long> usersIds;
}
