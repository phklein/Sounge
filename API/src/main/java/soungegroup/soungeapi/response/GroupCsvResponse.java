package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCsvResponse {
    @Schema(description = "ID do grupo",
            example = "3")
    private Long id;
    @Schema(description = "Nome do grupo",
            example = "Turma do Sertanejo")
    private String name;
    @Schema(description = "Descrição do grupo",
            example = "Grupo sertanejo de São Paulo")
    private String description;
    @Schema(description = "Data de criação do grupo",
            example = "2021-04-20")
    private LocalDate creationDate;
}
