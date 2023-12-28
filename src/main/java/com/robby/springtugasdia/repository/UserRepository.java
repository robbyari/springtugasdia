package com.robby.springtugasdia.repository;

import com.robby.springtugasdia.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT * FROM tab_robby", nativeQuery = true)
    List<UserModel> findByDataAllUser();

    @Query(value = "SELECT * FROM tab_robby WHERE user_name = :userName", nativeQuery = true)
    List<UserModel> findByDataNameUser(@Param("userName") String userName);

    Optional<UserModel> findByUserEmail(String userEmail);

}
