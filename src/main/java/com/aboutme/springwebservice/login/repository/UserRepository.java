package com.aboutme.springwebservice.login.repository;

import com.aboutme.springwebservice.login.domainModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
}
