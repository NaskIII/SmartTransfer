package com.wayon.SmartTransfer.controller;

import com.wayon.SmartTransfer.dto.error.ErrorResponse;
import com.wayon.SmartTransfer.dto.user.*;
import com.wayon.SmartTransfer.exceptions.NotEqualsException;
import com.wayon.SmartTransfer.exceptions.PasswordInUseException;
import com.wayon.SmartTransfer.exceptions.ResourceCreationException;
import com.wayon.SmartTransfer.service.TokenService;
import com.wayon.SmartTransfer.service.UserService;
import com.wayon.SmartTransfer.specifications.user.UserSearchCriteria;
import com.wayon.SmartTransfer.specifications.user.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final TokenService tokenService;

    @Autowired
    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserCreateRequest userCreateRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        try {
            var id = userService.createUser(userCreateRequest);

            var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();

        } catch (ResourceCreationException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotEqualsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The given password are not the same."));
        }
    }

    @GetMapping
    public Page<UserBasicResponse> search(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        UserSearchCriteria searchCriteria = UserSearchCriteria.builder()
                .name(params.getFirst("name"))
                .lastName(params.getFirst("lastName"))
                .email(params.getFirst("email"))
                .profile(params.getFirst("profile"))
                .status(params.getFirst("status"))
                .build();

        var specification = UserSpecifications.search(searchCriteria);
        return userService.search(specification, pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDetailResponse> getUserByUuid(@PathVariable String uuid) throws ChangeSetPersister.NotFoundException {
        try {
            var response = userService.getUserById(uuid);
            return ResponseEntity.ok(response);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDetailResponse> getUserBySession(HttpServletRequest request) {
        var token = request.getHeader("Authorization").split(" ");

        try {
            var response = userService.getUserById(tokenService.getUserId(token[1]));

            return ResponseEntity.ok(response);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDetailResponse> updateUser(@PathVariable String uuid,
                                                         @Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        try {
            var updatedUser = userService.updateUser(uuid, userUpdateRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uuid}/change-password")
    public ResponseEntity<?> updatePassword(@PathVariable String uuid,
                                            @Valid @RequestBody UserPasswordRequest userPasswordRequest) {
        try {
            var updatedUser = userService.updatePassword(uuid, userPasswordRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotEqualsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The given password are not the same."));
        } catch (PasswordInUseException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The new password cannot be the same as the current password."));
        }
    }
}
