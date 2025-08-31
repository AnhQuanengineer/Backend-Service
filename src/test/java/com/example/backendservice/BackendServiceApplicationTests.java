package com.example.backendservice;

import com.example.backendservice.controller.AuthenticationController;
import com.example.backendservice.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendServiceApplicationTests {

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authenticationController);
    }

}
