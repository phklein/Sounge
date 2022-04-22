package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleResponse {
    private Long id;
    private RoleName name;
}
