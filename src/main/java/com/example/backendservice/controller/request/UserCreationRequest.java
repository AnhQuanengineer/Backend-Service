package com.example.backendservice.controller.request;

import com.example.backendservice.common.UserGender;
import com.example.backendservice.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserCreationRequest implements Serializable {
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
    private UserType type;
//    private String address;
    private List<AddressRequest> addresses; // home, office
}
