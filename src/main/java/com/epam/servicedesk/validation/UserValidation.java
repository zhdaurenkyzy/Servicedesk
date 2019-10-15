package com.epam.servicedesk.validation;

import com.epam.servicedesk.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.epam.servicedesk.util.ConstantForApp.*;

public class UserValidation extends AbstractValidation {

    public static String validateName(String name) throws ValidationException {
        if (name.length() > NAME_MAX_LENGTH) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return name;
    }

    public static String validatePosition(String position) throws ValidationException {
        if (position.length() > POSITION_MAX_LENGTH) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return position;
    }

    public static String validatePhone(String phone) throws ValidationException {
        if (phone.length() > PHONE_MAX_LENGTH) {
            throw new ValidationException(INCORRECT_FORMAT);
        }
        return phone;
    }

    public static String validateLogin(String login) throws ValidationException {
        if ((login == null) || (EMPTY_STRING.equals(login) || (EMPTY_PLACE.equals(login)) ||
                (login.length() > MAX_LENGTH_OF_STRING) || (login.length() < MIN_LENGTH_OF_LOGIN) ||
                !doMatch(login, LOGIN_PATTERN))) {
            throw new ValidationException(LOGIN_INCORRECT_FORMAT);
        }
        return login;
    }

    public static String validatePassword(String password) throws ValidationException {
        if ((password == null) || (EMPTY_STRING.equals(password) || (EMPTY_PLACE.equals(password)) ||
                (password.length() > MAX_LENGTH_OF_STRING) || (password.length() < MIN_LENGTH_OF_PASSWORD))) {
            throw new ValidationException(PASSWORD_INCORRECT_FORMAT);
        }
        return password;
    }

    public static String validateMail(String mail) throws ValidationException {
        if ((mail.length() > MAIL_MAX_LENGTH) || (mail.length() < MAIL_MIN_LENGTH) ||
                !doMatch(mail, MAIL_PATTERN)) {
            throw new ValidationException(MAIL_INCORRECT_FORMAT);
        }
        return mail;
    }

    private static Boolean doMatch(String string, String regex) {
        boolean isMatch;
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        isMatch = matcher.matches();
        return isMatch;
    }
}
