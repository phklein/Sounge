package soungegroup.soungeapi.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Longitude {
    String message() default "Longitude deve ser um número entre -180 e 180";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
