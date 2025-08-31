package com.example.backendservice.controller.request;

import com.example.backendservice.common.UserGender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {
    @NotNull(message = "id not null")
    @Min(value = 1, message = "Id must be equal or greater than 1")
    private Long id;
    @NotBlank(message = "firstName not blank")
    private String firstName;
    @NotBlank(message = "lastName not blank")
    private String lastName;
    private UserGender gender;
    private Date birthday;
    private String username;
    @NotBlank(message = "Email not blank")
    private String email;
    private String phone;
    private List<AddressRequest> addresses;
}
