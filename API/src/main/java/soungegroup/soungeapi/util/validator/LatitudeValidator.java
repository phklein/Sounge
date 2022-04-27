package soungegroup.soungeapi.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements
        ConstraintValidator<Latitude, Double> {

    @Override
    public void initialize(Latitude latitude) {}

    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext cxt) {
        if (latitude != null) {
            return latitude >= -90 && latitude <= 90;
        }

        return false;
    }

}
