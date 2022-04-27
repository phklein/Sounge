package soungegroup.soungeapi.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements
        ConstraintValidator<Longitude, Double> {

    @Override
    public void initialize(Longitude longitude) {}

    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext cxt) {
        if (longitude != null) {
            return longitude >= -180 && longitude <= 180;
        }

        return false;
    }

}
