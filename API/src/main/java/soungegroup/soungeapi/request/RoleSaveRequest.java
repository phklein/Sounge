package soungegroup.soungeapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.RoleName;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleSaveRequest {
    private RoleName name;
    private LocalDate startDate;
}
