package com.aboutme.springwebservice.domain.repository;

import com.aboutme.springwebservice.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
