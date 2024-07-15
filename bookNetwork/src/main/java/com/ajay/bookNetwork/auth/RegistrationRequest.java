package com.ajay.bookNetwork.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "First Name is Mandatory")
    @NotBlank(message = "First Name is Mandatory")
    private String firstname;
    @NotEmpty(message = "Lastname Name is Mandatory")
    @NotBlank(message = "Lastname Name is Mandatory")
    private String lastname;
    @Email(message = "Email is not formated")
    @NotEmpty(message = "Email Name is Mandatory")
    @NotBlank(message = "Email Name is Mandatory")
    private String email;
    @NotEmpty(message = "Password Name is Mandatory")
    @NotBlank(message = "Password Name is Mandatory")
    @Size(min = 8,message = "Password should be of more then 8 char")
    private String password;
}
