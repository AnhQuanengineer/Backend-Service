package com.example.backendservice.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserPasswordRequest implements Serializable {
    @NotNull(message = "Id is not null")
    @Min(value = 1, message = "Id must be equal or greater than 1")
    private Long id;

    @NotBlank(message = "password not blank")
    private String password;

    @NotBlank(message = "confirmPassword not blank")
    private String confirmPassword;
}
