package com.wayon.SmartTransfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "Account number must be exactly 10 alphanumeric characters")
    @NotBlank
    private String sourceAccount;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    @NotBlank
    @Size(min = 8, max = 32)
    private String confirmPassword;

    @NotBlank @Pattern(regexp = "ADMINISTRATOR|COMMON", message = "Invalid profile name! Options: ADMINISTRATOR, COMMON")
    private String profile;
}
