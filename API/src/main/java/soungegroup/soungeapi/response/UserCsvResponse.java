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
    private Double latitude;
    @Schema(description = "Longitude do usuário (último registro coletado)",
            example = "-46.66155946066814")
    private Double longitude;
    @Schema(description = "Usuário é ou não é líder do grupo em que está",
            example = "São Paulo")
    private boolean isLeader;
    @Schema(description = "Nível de experiência do usuário com música",
            example = "ADVANCED")
    private SkillLevel skillLevel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }
}
