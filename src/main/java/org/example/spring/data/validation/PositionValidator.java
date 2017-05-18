package org.example.spring.data.validation;

import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates Position instances before saving Athletes to the data store.
 */
public class PositionValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Position.class == aClass;
    }

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
