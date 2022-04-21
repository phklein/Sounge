package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPageResponse {
    private Long id;
    private String name;
    private String description;
    private Integer age;
    private List<GenreSimpleResponse> genres;
    private List<UserSimpleResponse> users;
    private String pictureUrl;
}
