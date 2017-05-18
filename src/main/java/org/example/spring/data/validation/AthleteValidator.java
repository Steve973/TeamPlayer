package org.example.spring.data.validation;

import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates Athlete instances before saving to the data store.
 */
public class AthleteValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Athlete.class == aClass;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Athlete athlete = Athlete.class.cast(o);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Athlete name cannot be empty");
        if (athlete.getPosition() != null) {
            Position position = athlete.getPosition();
            errors.pushNestedPath("position");
            ValidationUtils.invokeValidator(new PositionValidator(), position, errors);
            errors.popNestedPath();
        }
    }
}
