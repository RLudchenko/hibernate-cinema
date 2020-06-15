package com.dev.cinema.annotation;

import com.dev.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator {

    public boolean isValid(UserRequestDto userRequestDto, ConstraintValidatorContext
            constraintValidatorContext) {
        String password = userRequestDto.getPassword();
        String repeatPassword = userRequestDto.getPassword();
        return password != null && password.equals(repeatPassword);
    }
}
