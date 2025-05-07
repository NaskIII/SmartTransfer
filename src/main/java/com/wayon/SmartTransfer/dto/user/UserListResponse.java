package com.wayon.SmartTransfer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserListResponse {

    private List<UserDetailResponse> users;

    private int total;
}
