package com.wayon.SmartTransfer.service;

import com.wayon.SmartTransfer.dto.user.*;
import com.wayon.SmartTransfer.entity.user.User;
import com.wayon.SmartTransfer.exceptions.NotEqualsException;
import com.wayon.SmartTransfer.exceptions.PasswordInUseException;
import com.wayon.SmartTransfer.exceptions.ResourceCreationException;
import com.wayon.SmartTransfer.mapper.IUserMapper;
import com.wayon.SmartTransfer.repository.IProfileRepository;
import com.wayon.SmartTransfer.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(UserCreateRequest userCreateRequest) throws ResourceCreationException, NotEqualsException {
        if (!Objects.equals(userCreateRequest.getPassword(), userCreateRequest.getConfirmPassword())) {
            throw new NotEqualsException();
        }

        var profile = profileRepository.findByName(userCreateRequest.getProfile()).orElseThrow(ResourceCreationException::new);

        var user = IUserMapper.INSTANCE.CreateUserRequestToUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setProfile(profile);

        var createdUser = userRepository.save(user);

        return createdUser.getUserId();
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserDetailResponse getUserById(String uuid) throws ChangeSetPersister.NotFoundException {
        var user = userRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return IUserMapper.INSTANCE.UserToUserDetailResponse(user);
    }

    public UserDetailResponse getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(ChangeSetPersister.NotFoundException::new);
        var profile = profileRepository.findById(user.getProfile().getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        var response = IUserMapper.INSTANCE.UserToUserDetailResponse(user);
        response.setProfile(profile.getName());

        return response;
    }

    public Page<UserBasicResponse> search(Specification<User> specification, Pageable pageable) {
        var users = userRepository.findAll(specification, pageable);

        return IUserMapper.INSTANCE.UserToPageUserBasicResponse(users);
    }

    public UserDetailResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws ChangeSetPersister.NotFoundException {
        var profile = profileRepository.findByName(userUpdateRequest.getProfile()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        var user = userRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setProfile(profile);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setStatus(userUpdateRequest.getStatus());

        userRepository.save(user);

        return IUserMapper.INSTANCE.UserToUserDetailResponse(user);
    }

    public UserDetailResponse updatePassword(String uuid, UserPasswordRequest userPasswordRequest) throws ChangeSetPersister.NotFoundException, NotEqualsException, PasswordInUseException {
        if (!Objects.equals(userPasswordRequest.getPassword(), userPasswordRequest.getConfirmPassword())) {
            throw new NotEqualsException();
        }

        var user = userRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (passwordEncoder.matches(userPasswordRequest.getPassword(), user.getPassword())) {
            throw new PasswordInUseException();
        }

        user.setPassword(passwordEncoder.encode(userPasswordRequest.getPassword()));
        userRepository.save(user);

        return IUserMapper.INSTANCE.UserToUserDetailResponse(user);
    }
}
