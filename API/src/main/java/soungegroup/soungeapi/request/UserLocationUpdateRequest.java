package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import soungegroup.soungeapi.util.validator.Latitude;
import soungegroup.soungeapi.util.validator.Longitude;

import javax.validation.constraints.NotNull;

@Data
public class UserLocationUpdateRequest {
    @NotNull
    @Latitude
    @Schema(description = "Latitude nova",
            example = "-23.558029632001297")
    private Double latitude;
    @NotNull
    @Longitude
    @Schema(description = "Longitude nova",
            example = "-46.66029420331474")
    private Double longitude;

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
}
