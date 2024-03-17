package com.plum.demo.service.validate;

import com.plum.demo.exception.CustomException;
import com.plum.demo.service.BaseService;
import com.plum.demo.util.contants.ValidationConstants;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ValidationService<T> extends BaseService {
    abstract public void validate(T t) throws CustomException;

    protected void validatePhone(String phoneNumber) throws CustomException {
        phoneNumber = phoneNumber.replaceAll("\\s", "");
        boolean isValidPhone = Pattern.compile(ValidationConstants.PHONE_NUMBER_REGEX)
                .matcher(phoneNumber)
                .matches();
        if (!isValidPhone) {
            throw new CustomException("Phone Number is Invalid");
        }
    }

    protected void validateEmail(String emailAddress) throws CustomException {
        emailAddress = emailAddress.replaceAll("\\s", "");
        boolean isValidEmail = Pattern.compile(ValidationConstants.EMAIL_REGEX_EXPRESSION)
                .matcher(emailAddress)
                .matches();
        if (!isValidEmail) {
            throw new CustomException("Email is InValid");
        }
    }

    protected void validateName(String name) throws CustomException {
        name = name.replaceAll("\\s", "");
        boolean isValidName = name.trim().chars().allMatch(Character::isLetter) &&
                name.length() >= 3;
        if (!isValidName) {
            throw new CustomException("Name is should be or size 3");
        }
    }
}
