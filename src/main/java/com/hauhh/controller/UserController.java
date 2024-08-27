package com.hauhh.controller;

import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.request.UserUpdateRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create new User",
            description = "Create new User"
    )
    @PostMapping
    public ResponseData<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ResponseData.<UserResponse>builder()
                .message("Create user")
                .result(userService.createUser(request))
                .build();
    }

    @Operation(
            summary = "Get all user",
            description = "Get all User from Database"
    )
    @GetMapping
    public ResponseData<List<UserResponse>> getAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}",authentication.getName());
        log.info("Role: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<List<UserResponse>>builder()
                .message("Get all users")
                .result(userService.getAllUser())
                .build();
    }

    @Operation(
            summary = "Get Info",
            description = "Get User info using JWT"
    )
    @GetMapping("/myInfo")
    public ResponseData<UserResponse> getMyInfo() {
        return ResponseData.<UserResponse>builder()
                .message("Get user info")
                .result(userService.getUserInfo())
                .build();
    }

    @Operation(
            summary = "Get User",
            description = "Get User from database"
    )
    @GetMapping("/getUser/{userID}")
    public ResponseData<UserResponse> getUser(@PathVariable String userID) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        log.info("Role: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<UserResponse>builder()
                .message("Get user info")
                .result(userService.findUserByID(userID))
                .build();
    }

    @Operation(
            summary = "Update User",
            description = "Update existing User"
    )
    @PutMapping("/{userID}")
    public ResponseData<UserResponse> updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request) {
        return ResponseData.<UserResponse>builder()
                .message("Update user")
                .result(userService.updateUser(userID, request))
                .build();
    }

    @Operation(
            summary = "Delete User",
            description = "Delete User By ID"
    )
    @DeleteMapping("/{userID}")
    public ResponseData<Void> deleteUser(@PathVariable String userID) {
        userService.deleteUserByID(userID);
        return ResponseData.<Void>builder()
                .message("Delete user")
                .build();
    }


}
