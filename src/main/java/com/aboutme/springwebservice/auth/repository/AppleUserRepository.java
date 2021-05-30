package com.aboutme.springwebservice.auth.repository;

import com.aboutme.springwebservice.auth.domainModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppleUserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
}
