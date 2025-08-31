package com.example.backendservice.controller;

import com.example.backendservice.common.UserGender;
import com.example.backendservice.controller.request.UserCreationRequest;
import com.example.backendservice.controller.request.UserPasswordRequest;
import com.example.backendservice.controller.request.UserUpdateRequest;
import com.example.backendservice.controller.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mockup/user")
@Tag(name = "Mockup User Controller")
public class MockupUserController {
    //    đối với api phải bắt đầu bằng public trước
    @Operation(summary = "Get user list", description = "API retrieve user from db")
    @GetMapping("/list")
    //    @RequestParam(required = false) String keyword: nếu user truyền keyword thì đưa ra danh sách theo keyword
//    còn ko thì trả tất.
//    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size : phân trang,
//    nếu user ko truyền gì thì chạy từ 0 .

    public Map<String, Object> getList(@RequestParam(required = false) String keyword
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "20") int size ){

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1L);
        userResponse1.setFirstName("Anh");
        userResponse1.setLastName("Quan");
        userResponse1.setGender(UserGender.MALE);
        userResponse1.setBirthday(new Date());
        userResponse1.setUsername("admin");
        userResponse1.setEmail("admin@gmail.com");
        userResponse1.setPhone("0955678234");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFirstName("Minh");
        userResponse2.setLastName("Anh");
        userResponse2.setGender(UserGender.MALE);
        userResponse2.setBirthday(new Date());
        userResponse2.setUsername("user");
        userResponse2.setEmail("xinh@gmail.com");
        userResponse2.setPhone("0955678234");

        List<UserResponse> userList= List.of(userResponse1, userResponse2);

//        trả về reponse
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userList);

        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId){

        UserResponse userDetail= new UserResponse();
        userDetail.setId(userId);
        userDetail.setFirstName("Anh");
        userDetail.setLastName("Quan");
        userDetail.setGender(UserGender.MALE);
        userDetail.setBirthday(new Date());
        userDetail.setUsername("admin");
        userDetail.setEmail("admin@gmail.com");
        userDetail.setPhone("0955678234");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user");
        result.put("data", userDetail);

        return result;
    }

    @Operation(summary = "Create user", description = "API add user to db")
    @PostMapping("/add")
    public Map<String, Object> createUser(UserCreationRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user created successfully");
        result.put("data", 3);

        return result;
    }

    @Operation(summary = "Update user", description = "API update user to db")
    @PutMapping("/update")
    public Map<String, Object> updateUser(UserUpdateRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "user updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change password", description = "API change password for user to db")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(UserPasswordRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Inactivate User", description = "API inactivate user")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "user deleted successfully");
        result.put("data", "");

        return result;
    }
}
