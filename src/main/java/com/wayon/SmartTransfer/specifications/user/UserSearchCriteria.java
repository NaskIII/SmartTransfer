package com.wayon.SmartTransfer.specifications.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSearchCriteria {

    private String name;

    private String lastName;

    private String email;

    private String profile;

    private String status;

}
