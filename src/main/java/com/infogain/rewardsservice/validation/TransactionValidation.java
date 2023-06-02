package com.infogain.rewardsservice.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TransactionValidation {

    private static Validator validator;

    public TransactionValidation(Validator validator) {
        this.validator = validator;
    }

    public static <T> void validate(T request) {
        var violations = validator.validate(request);
        if (isInvalid(violations)) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {
                errors.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Request invalid: " + errors, violations);
        }
    }

    private static <T> boolean isInvalid(Set<ConstraintViolation<T>> violations) {
        return !violations.isEmpty();
    }
}
