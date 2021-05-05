package com.aboutme.springwebservice.mypage.repository;

import com.aboutme.springwebservice.mypage.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
