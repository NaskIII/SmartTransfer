package com.wayon.SmartTransfer.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailResponse {

    private String userId;

    private String profile;

    private String firstName;

    private String lastName;

    private String email;

    private String status;

    private String createdBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String updatedBy;
}
