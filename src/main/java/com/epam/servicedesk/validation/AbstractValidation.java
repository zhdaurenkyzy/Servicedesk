package com.epam.servicedesk.validation;

import com.epam.servicedesk.exception.ValidationException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public abstract class AbstractValidation {

    public static Long validateLong(String string) throws ValidationException {
        long longNumeric;
        try {
            longNumeric = Long.parseLong(string);
        } catch (NumberFormatException e) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return longNumeric;
    }

    public static Long validateId(String id) throws ValidationException {
        if (id.equals("null")) {
            id = "0";

        }
        return validateLong(id);
    }

    static String validateString(String string, Integer maxLength) throws ValidationException {
        if ((string == null) || (EMPTY_STRING.equals(string) || (EMPTY_PLACE.equals(string)) || (string.length() > maxLength))) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return string;
    }

    public static boolean isNumeric(String string){
        try {
            Long.parseLong(string);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
