package soungegroup.soungeapi.domain.model.relations.idclass;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberHasRoleId implements Serializable {
    private Long member;
    private Long role;
}
