package com.example.ProjectLogin.repositories;

import com.example.ProjectLogin.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <UserModel, Integer> {

    Optional<UserModel> findByUsername(String username);
    @Query(value="SELECT * FROM USERS", nativeQuery = true)
    List<UserModel> getAllUsers();

    @Modifying
    @Transactional
    @Query(value="UPDATE users u SET u.first_name = :firstname, u.last_name = :lastname, u.password = :password WHERE u.username = :username", nativeQuery = true)
    void updateUserDetails(String username, String firstname, String lastname, String password);

    @Modifying
    @Transactional
    void deleteByUsername(String username);
}