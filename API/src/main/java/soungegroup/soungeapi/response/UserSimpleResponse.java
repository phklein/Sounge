package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {
    private Long id;
    private String name;
    private Sex sex;
}
