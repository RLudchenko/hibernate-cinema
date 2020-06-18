package com.dev.cinema.model.dto;

import com.dev.cinema.annotation.EmailConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestDto {
    @EmailConstraint
    private String email;
    @NotNull(message = "Password cannot be empty!")
    @Size(min = 3, message = "Password must be greater or equal to 3!")
    private String password;
    @NotNull(message = "Repeat password cannot be empty!")
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
