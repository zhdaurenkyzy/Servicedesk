package com.epam.servicedesk.validation;

import com.epam.servicedesk.exception.ValidationException;
import static com.epam.servicedesk.util.ConstantForApp.*;

public class GroupAndProjectValidation extends AbstractValidation {

    public static String validateNameGroupOrProject(String name) throws ValidationException {
        return validateString(name, GROUP_OR_PROJECT_MAX_LENGTH);
    }
}
