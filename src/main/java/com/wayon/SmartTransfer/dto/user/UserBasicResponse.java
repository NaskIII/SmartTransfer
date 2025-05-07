package com.wayon.SmartTransfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor

public class UserBasicResponse {
    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String profile;

    private String status;
}
