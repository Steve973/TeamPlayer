package org.example.spring.data.validation;

import org.example.spring.model.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates Team instances before saving to the data store.
 */
public class TeamValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Team.class == aClass;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Team name cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "key", "field.required", "Team key cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conference", "field.required", "Team conference cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "division", "field.required", "Team division cannot be empty");
    }
}
