package com.example.backendservice.controller;

import com.example.backendservice.controller.request.UserCreationRequest;
import com.example.backendservice.controller.request.UserPasswordRequest;
import com.example.backendservice.controller.request.UserUpdateRequest;
import com.example.backendservice.controller.response.ApiResponse;
import com.example.backendservice.controller.response.UserPageResponse;
import com.example.backendservice.controller.response.UserResponse;
import com.example.backendservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@Slf4j(topic = "USER-CONTROLLER")
@RequiredArgsConstructor
@Validated
public class UserController {
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Operation(summary = "Get user list", description = "API retrieve user from db")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('manager', 'admin')")

    public ApiResponse getList(@RequestParam(required = false) String keyword
    , @RequestParam(required = false) String sort
    , @RequestParam(defaultValue = "0") int page
    , @RequestParam(defaultValue = "20") int size ){
        log.info("Get user list");

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("users")
                .data(userService.findAll(keyword, sort, page, size))
                .build();
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('user')")
    public ApiResponse getUserDetail(@PathVariable @Min(value = 1
            , message = "userId must be equal or greater than 1") Long userId){
        log.info("get user detail by id: {}", userId);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("user ")
                .data(userService.findById(userId))
                .build();
    }

    @Operation(summary = "Create user", description = "API add user to db")
    @PostMapping("/add")
    public ApiResponse createUser(@RequestBody @Valid UserCreationRequest request){
        log.info("create user: {}", request);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("user created successfully")
                .data(userService.save(request))
                .build();
    }

    @Operation(summary = "Update user", description = "API update user to db")
    @PutMapping("/update")
    public ApiResponse updateUser(@RequestBody @Valid UserUpdateRequest request){
        log.info("Update user request: {}", request);

        userService.update(request);

        return ApiResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("user updated successfully")
                .build();
    }

    @Operation(summary = "Change password", description = "API change password for user to db")
    @PatchMapping("/change-pwd")
    public ApiResponse changePassword(@RequestBody @Valid UserPasswordRequest request){
        log.info("Change password request: {}", request);

        userService.changePassword(request);

        return ApiResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("password updated successfully")
                .build();
    }

    @Operation(summary = "Inactivate User", description = "API inactivate user")
    @DeleteMapping("/del/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse deleteUser(@PathVariable @Min(value = 1
            , message = "Id must be equal or greater than 1") Long userId){
        log.info("Delete user request: {}", userId);

        return ApiResponse.builder()
                .status(HttpStatus.RESET_CONTENT.value())
                .message("user deleted successfully")
                .build();
    }
}
