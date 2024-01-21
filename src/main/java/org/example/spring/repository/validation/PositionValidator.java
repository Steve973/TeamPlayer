package org.example.spring.repository.validation;

import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates Position instances before saving Athletes to the data store.
 */
public class PositionValidator implements Validator {
    /**
     * Ensures that this class only validates Position objects.
     *
     * @param aClass the class to be validated
     * @return true if the specified class is Position
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Position.class == aClass;
    }

    /**
     * Validates Position objects.  Position has a Team field, so this method
     * creates a TeamValidator and invokes it.  The field path has to be adjusted
     * to look in this object, so it is adjusted, then returned after validation.
     *
     * @param o the object to be validated
     * @param errors any errors that are encountered in validation
     */
    @Override
    public void validate(Object o, Errors errors) {
        Position position = Position.class.cast(o);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Position name cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jersey", "field.required", "Position jersey cannot be empty");
        Team team = position.getTeam();
        errors.pushNestedPath("team");
        ValidationUtils.invokeValidator(new TeamValidator(), team, errors);
        errors.popNestedPath();
    }
}
