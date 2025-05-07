package com.wayon.SmartTransfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordRequest {

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    @NotBlank
    private String confirmPassword;
}
