package com.epam.servicedesk.validation;

import com.epam.servicedesk.exception.ValidationException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.INCORRECT_FORMAT;

public class RequestValidation extends AbstractValidation {

    public static String validateTheme(String theme) throws ValidationException {
        return validateString(theme, THEME_MAX_LENGTH);
    }

    public static String validateDescriptionOrDecision(String string) throws ValidationException {
        if (string.length() > DESCRIPTION_OR_DECISION_MAX_LENGTH) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return string;
    }
}
