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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
