package com.example.backendservice.repository;

import com.example.backendservice.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u FROM UserEntity u WHERE u.status ='ACTIVE' " +
            "AND (lower(u.firstName) like :keyword " +
            "or lower(u.lastName) like :keyword " +
            "or lower(u.username) like :keyword " +
            "or lower(u.phone) like :keyword " +
            "or lower(u.email) like :keyword)")
    Page<UserEntity> searchByKeyword(String keyword, Pageable pageable);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
