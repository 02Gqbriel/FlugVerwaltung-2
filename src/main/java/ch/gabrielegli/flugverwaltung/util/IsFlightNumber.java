package ch.gabrielegli.flugverwaltung.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsFlightNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsFlightNumber {
    String message() default "Invalid Flight-Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
