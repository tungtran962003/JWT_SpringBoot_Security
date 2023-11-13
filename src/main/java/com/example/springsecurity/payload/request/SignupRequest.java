package com.example.springsecurity.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @Size(max = 20, message = "Username maximum 20 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(max = 50, message = "Email maximum 50 characters")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    private Set<String> role;

    @Size(max = 100, message = "Maximum password 100 characters")
    @NotBlank(message = "Password cannot be left blank")
    private String password;


}
