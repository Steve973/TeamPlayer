package org.example.spring.data.validation;

import org.example.spring.model.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates Team instances before saving to the data store.
 */
public class TeamValidator implements Validator {
    /**
     * Ensures that this class only validates Team objects.
     *
     * @param aClass the class to be validated
     * @return true if the specified class is Position
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Team.class == aClass;
    }

    /**
     * Validates Team objects.
     *
     * @param o the object to be validated
     * @param errors any errors that are encountered in validation
     */
    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Team name cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "key", "field.required", "Team key cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conference", "field.required", "Team conference cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "division", "field.required", "Team division cannot be empty");
    }
}
