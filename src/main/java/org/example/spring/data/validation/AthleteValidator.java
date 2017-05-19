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
    /**
     * Ensures that this class only validates Athlete objects.
     *
     * @param aClass the class to be validated
     * @return true if the specified class is Athlete
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Athlete.class == aClass;
    }

    /**
     * Validates Athlete objects.  Athlete has a Position field, so this method
     * creates a PositionValidator and invokes it.  The field path has to be adjusted
     * to look in this object, so it is adjusted, then returned after validation.
     *
     * @param o the object to be validated
     * @param errors any errors that are encountered in validation
     */
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
