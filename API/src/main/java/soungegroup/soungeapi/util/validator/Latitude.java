package soungegroup.soungeapi.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Latitude {
    String message() default "Latitude deve ser um número entre -90 e 90";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
