package com.example.backendservice.controller.response;

import com.example.backendservice.common.UserGender;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private UserGender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
}
