package com.aboutme.springwebservice.domain.repository;

import com.aboutme.springwebservice.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findByUserID(long authorId);

    UserProfile findOneByUserID(long authorId);
}
