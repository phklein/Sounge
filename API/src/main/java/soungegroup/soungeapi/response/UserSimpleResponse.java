package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {
    private Long id;
    private String name;
    private String profilePic;
    private List<RoleSimpleResponse> roles;
    private boolean leader;
}
