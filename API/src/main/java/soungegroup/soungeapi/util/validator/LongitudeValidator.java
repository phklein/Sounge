package soungegroup.soungeapi.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements
        ConstraintValidator<Longitude, String> {

    @Override
    public void initialize(Longitude longitude) {}

    @Override
    public boolean isValid(String longitude, ConstraintValidatorContext cxt) {
        double value;

        if (longitude != null) {
            try {
                value = Double.parseDouble(longitude);
                return value >= -180 && value <= 180;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

}
