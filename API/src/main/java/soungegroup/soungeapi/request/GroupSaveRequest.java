package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Data
public class GroupSaveRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @PastOrPresent
    private LocalDate creationDate;
    @NotEmpty
    private List<GenreName> genres;
}
