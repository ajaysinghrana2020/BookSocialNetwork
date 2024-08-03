package com.ajay.bookNetwork.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is not formated")
    @NotEmpty(message = "Email Name is Mandatory")
    @NotNull(message = "Email Name is Mandatory")
    private String email;
    @NotEmpty(message = "Password Name is Mandatory")
    @NotNull(message = "Password Name is Mandatory")
    @Size(min = 8,message = "Password should be of more then 8 char")
    private String password;
}
