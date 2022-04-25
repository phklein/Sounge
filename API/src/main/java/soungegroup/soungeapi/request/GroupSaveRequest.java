package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Data
public class GroupSaveRequest {
    @NotBlank
    @Schema(description = "Nome do grupo",
            example = "Reborn")
    private String name;
    @Schema(description = "Descrição do grupo",
            example = "Banda de metal!!!!!!")
    private String description;
    @NotNull
    @PastOrPresent
    @Schema(description = "Data da criação do grupo",
            format = "yyyy-MM-dd",
            example = "2021-04-20")
    private LocalDate creationDate;
    @NotEmpty
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais relacionados ao grupo",
            example = "[\"METAL\", \"ROCK\"]"))
    private List<GenreName> genres;
}
