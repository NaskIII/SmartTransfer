package com.wayon.SmartTransfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank
    private String profile;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "Account number must be exactly 10 alphanumeric characters")
    @NotBlank
    private String sourceAccount;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String status;
}
