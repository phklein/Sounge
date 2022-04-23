package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCsvResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate creationDate;
}
