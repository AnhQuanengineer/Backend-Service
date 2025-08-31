package com.example.backendservice.controller;

import com.example.backendservice.common.UserGender;
import com.example.backendservice.common.UserStatus;
import com.example.backendservice.common.UserType;
import com.example.backendservice.controller.response.UserPageResponse;
import com.example.backendservice.controller.response.UserResponse;
import com.example.backendservice.model.UserEntity;
import com.example.backendservice.service.JwtService;
import com.example.backendservice.service.UserService;
import com.example.backendservice.service.UserServiceDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserServiceDetail userServiceDetail;

    @MockitoBean
    private JwtService jwtService;

    private static UserResponse anhquan;
    private static UserResponse minhanh;


    @BeforeAll
    static void setUp() {
        anhquan = new UserResponse();
        anhquan.setId(1L);
        anhquan.setFirstName("Anh");
        anhquan.setLastName("Quan");
        anhquan.setGender(UserGender.MALE);
        anhquan.setBirthday(new Date());
        anhquan.setEmail("anhquan@gmail.com");
        anhquan.setUsername("anhquan");

        minhanh = new UserResponse();
        minhanh.setId(2L);
        minhanh.setFirstName("Minh");
        minhanh.setLastName("Anh");
        minhanh.setGender(UserGender.FEMALE);
        minhanh.setBirthday(new Date());
        minhanh.setEmail("minhanh@gmail.com");
        minhanh.setUsername("minhanh");
    }

    @Test
    @WithMockUser(authorities = {"admin", "manager"})
    void testGetUser() throws Exception {
        List<UserResponse> userResponses = List.of(anhquan, minhanh);

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(0);
        userPageResponse.setPageSize(20);
        userPageResponse.setTotalPages(1);
        userPageResponse.setTotalElements(2);
        userPageResponse.setUsers(userResponses);

        when(userService.findAll(null, null, 0, 20)).thenReturn(userPageResponse);

        mockMvc.perform(get("/user/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.message", is("users")))
                .andExpect(jsonPath("$.data.totalElements", is(2)));

    }

}
