package com.dev.cinema.validator;

import com.dev.cinema.annotation.PasswordConstraint;
import com.dev.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, UserRequestDto> {

    public boolean isValid(UserRequestDto userRequestDto, ConstraintValidatorContext
            constraintValidatorContext) {
        String password = userRequestDto.getPassword();
        String repeatPassword = userRequestDto.getPassword();
        return password != null && password.equals(repeatPassword);
    }
}
