package ch.gabrielegli.flugverwaltung.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsFlightNumberValidator implements ConstraintValidator<IsFlightNumber, String> {
    @Override
    public void initialize(IsFlightNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String aString, ConstraintValidatorContext constraintValidatorContext) {
        return aString.matches("[a-zA-Z]{2}[\\s.][\\d]{3}") && aString != null && aString.length() == 6;
    }
}
