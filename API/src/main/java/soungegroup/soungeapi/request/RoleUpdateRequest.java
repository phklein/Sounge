package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.RoleName;

import java.util.List;

@Data
public class RoleUpdateRequest {
    private List<RoleName> toAdd;
    private List<RoleName> toRemove;
}
