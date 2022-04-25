package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCsvResponse {
    @Schema(description = "ID do usuário",
            example = "6")
    private Long id;
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;
    @Schema(description = "Sexo do usuário",
            example = "MALE")
    private Sex sex;
    @Schema(description = "Descrição do usuário",
            example = "Toco guitarra desde pequeno, sou apaixonado por rock")
    private String description;
    @Schema(description = "Data de nascimento do usuário",
            format = "yyy-MM-dd",
            example = "2003-05-12")
    private LocalDate birthDate;
    @Schema(description = "Estado de residência do usuário",
            example = "SP")
    private State state;
    @Schema(description = "Cidade de residência do usuário",
            example = "São Paulo")
    private String city;
    @Schema(description = "Latitude do usuário (último registro coletado)",
            example = "-23.55793998778981")
    private String latitude;
    @Schema(description = "Longitude do usuário (último registro coletado)",
            example = "-46.66155946066814")
    private String longitude;
    @Schema(description = "Usuário é ou não é líder do grupo em que está",
            example = "São Paulo")
    private boolean leader;
    @Schema(description = "Nível de experiência do usuário com música",
            example = "ADVANCED")
    private SkillLevel skillLevel;
}
