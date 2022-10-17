package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class GroupSaveRequest {
    @NotNull
    @Positive
    @Schema(description = "Id do líder do grupo",
            example = "6")
    private Long leaderId;
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

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<GenreName> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreName> genres) {
        this.genres = genres;
    }
}
