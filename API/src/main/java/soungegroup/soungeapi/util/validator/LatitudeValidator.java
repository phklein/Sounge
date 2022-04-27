package soungegroup.soungeapi.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements
        ConstraintValidator<Latitude, String> {

    @Override
    public void initialize(Latitude latitude) {}

    @Override
    public boolean isValid(String latitude, ConstraintValidatorContext cxt) {
        double value;

        if (latitude != null) {
            try {
                value = Double.parseDouble(latitude);
                return value >= -90 && value <= 90;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

}
