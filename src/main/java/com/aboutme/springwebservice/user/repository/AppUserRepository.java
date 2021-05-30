package com.aboutme.springwebservice.user.repository;

import com.aboutme.springwebservice.user.entitiy.AppUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserInfo, Long> {
    Optional<AppUserInfo> findByNaverUserId(long naverUserId);
}
