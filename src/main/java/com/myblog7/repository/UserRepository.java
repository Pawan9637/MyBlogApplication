package com.myblog7.repository;

import com.myblog7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

//    The return type Optional<User> in the findByEmail method indicates
//    that it might return a User object if found in the database or
//    an empty result (Optional.empty()) if no matching user is found.
//    This helps handle null values more safely and makes the code more
//    concise by avoiding explicit null checks.
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username,String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
