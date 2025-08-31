package com.example.backendservice.service;

import com.example.backendservice.common.UserGender;
import com.example.backendservice.common.UserStatus;
import com.example.backendservice.common.UserType;
import com.example.backendservice.controller.request.AddressRequest;
import com.example.backendservice.controller.request.UserCreationRequest;
import com.example.backendservice.controller.request.UserUpdateRequest;
import com.example.backendservice.controller.response.UserPageResponse;
import com.example.backendservice.controller.response.UserResponse;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.model.UserEntity;
import com.example.backendservice.repository.AddressRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    private @Mock UserRepository userRepository;
    private @Mock AddressRepository addressRepository;
    private @Mock PasswordEncoder passwordEncoder;

    private static UserEntity anhquan;
    private static UserEntity minhanh;

    @BeforeAll
    static void beforeAll(){
        anhquan = new UserEntity();
        anhquan.setId(1L);
        anhquan.setFirstName("Anh");
        anhquan.setLastName("Quan");
        anhquan.setGender(UserGender.MALE);
        anhquan.setBirthday(new Date());
        anhquan.setEmail("anhquan@gmail.com");
        anhquan.setUsername("anhquan");
        anhquan.setPassword("password");
        anhquan.setType(UserType.USER);
        anhquan.setStatus(UserStatus.ACTIVE);

        minhanh = new UserEntity();
        minhanh.setId(2L);
        minhanh.setFirstName("Minh");
        minhanh.setLastName("Anh");
        minhanh.setGender(UserGender.FEMALE);
        minhanh.setBirthday(new Date());
        minhanh.setEmail("minhanh@gmail.com");
        minhanh.setUsername("minhanh");
        minhanh.setPassword("password");
        minhanh.setType(UserType.USER);
        minhanh.setStatus(UserStatus.INACTIVE);
    }

    @BeforeEach
    void setUp() {
        // create userService
        userService = new UserServiceImpl(userRepository, addressRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetListUsers_Success() {
        // simulator
        Page<UserEntity> usePage = new PageImpl<>(Arrays.asList(anhquan, minhanh));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(usePage);

        // call function for test
        UserPageResponse result = userService.findAll(null, null, 0, 20);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testSearchUsers_Success() {
        // simulator
        Page<UserEntity> usePage = new PageImpl<>(Arrays.asList(anhquan, minhanh));
        when(userRepository.searchByKeyword(any(), any(Pageable.class))).thenReturn(usePage);

        // call function for test
        UserPageResponse result = userService.findAll("Anh", null, 0, 20);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testGetListUser_Empty() {
        // simulator
        Page<UserEntity> usePage = new PageImpl<>(List.of());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(usePage);

        // call function for test
        UserPageResponse result = userService.findAll(null, null, 0, 20);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testGetUserById_Success() {
        // simulator
        when(userRepository.findById(1L)).thenReturn(Optional.of(anhquan));

        UserResponse result = userService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserById_Failure() {
        // simulator
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(2L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByUsername() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void testSaveUser_Success() {

        when(userRepository.save(any(UserEntity.class))).thenReturn(anhquan);

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setFirstName("Anh");
        userCreationRequest.setLastName("Quan");
        userCreationRequest.setGender(UserGender.MALE);
        userCreationRequest.setBirthday(new Date());
        userCreationRequest.setEmail("anhquan@gmail.com");
        userCreationRequest.setPhone("098543225");
        userCreationRequest.setUsername("anhquan");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);

        userCreationRequest.setAddresses(List.of(addressRequest));

        long userId = userService.save(userCreationRequest);

        assertEquals(1L, userId);

    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 2L;

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Dao Ngoc");
        updatedUser.setLastName("Minh Anh");
        updatedUser.setGender(UserGender.FEMALE);
        updatedUser.setBirthday(new Date());
        updatedUser.setEmail("minhanh@gmail.com");
        updatedUser.setPhone("098543225");
        updatedUser.setUsername("minh anh xinh nhat");
        updatedUser.setType(UserType.USER);
        updatedUser.setStatus(UserStatus.ACTIVE);

        // simulation
        when(userRepository.findById(userId)).thenReturn(Optional.of(minhanh));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(userId);
        userUpdateRequest.setFirstName("Dao Ngoc");
        userUpdateRequest.setLastName("Minh Anh");
        userUpdateRequest.setGender(UserGender.FEMALE);
        userUpdateRequest.setBirthday(new Date());
        userUpdateRequest.setEmail("minhanh@gmail.com");
        userUpdateRequest.setPhone("098543225");
        userUpdateRequest.setUsername("minh anh xinh nhat");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);

        userUpdateRequest.setAddresses(List.of(addressRequest));

        userService.update(userUpdateRequest);

        UserResponse result = userService.findById(userId);
        assertNotNull(result);
        assertEquals("Dao Ngoc", result.getFirstName());
        assertEquals("Minh Anh", result.getLastName());

    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(anhquan));
        userService.delete(userId);
        assertEquals(UserStatus.INACTIVE, anhquan.getStatus());
        verify(userRepository, times(1)).save(anhquan);
    }

    @Test
    void changePassword() {
    }
}