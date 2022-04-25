package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupPageUpdateRequest {
    @NotBlank
    @Schema(description = "Nome do grupo a ser inserido",
            example = "Rebirth")
    private String name;
    @Schema(description = "Descrição do grupo a ser inserida",
            example = "Banda de rock!")
    private String description;
}
