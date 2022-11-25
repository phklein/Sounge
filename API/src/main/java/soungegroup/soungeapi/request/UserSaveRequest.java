package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import soungegroup.soungeapi.enums.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter @Setter
public class UserSaveRequest {
    @Email
    @NotBlank
    @Schema(description = "Email do usuário",
            example = "robson@gmail.com")
    private String email;
    @Length(min = 8)
    @NotNull
    @Schema(description = "Senha do usuário",
            minLength = 8,
            example = "12345678")
    private String password;
    @NotBlank
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;

    @Schema(description = "Sexo do usuário",
            example = "MALE")
    private Sex sex;
    @Schema(description = "Descrição do usuário",
            example = "Toco guitarra desde pequeno, sou apaixonado por rock")
    private String description;
    @Past

    @Schema(description = "Data de nascimento do usuário",
            format = "yyy-MM-dd",
            example = "2003-05-12")
    private LocalDate birthDate;
    @NotNull
    @Schema(description = "Estado de residência do usuário",
            example = "SP")
    private State state;
    @NotBlank
    @Schema(description = "Cidade de residência do usuário",
            example = "São Paulo")
    private String city;
    @NotEmpty
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais de interesse do usuário",
            example = "[\"METAL\", \"ROCK\"]"))
    private List<GenreName> likedGenres;
    @NotEmpty
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Funções desempenhadas pelo usuário",
            example = "[\"GUITARIST\", \"VOCALIST\"]"))
    private List<RoleName> roles;
    @NotNull
    @Schema(description = "Nível de experiência do usuário com música",
            example = "ADVANCED")
    private SkillLevel skillLevel;
    @Schema(description = "Telefone do usuário",
            example = "11999999999")
    private String phone;
}
