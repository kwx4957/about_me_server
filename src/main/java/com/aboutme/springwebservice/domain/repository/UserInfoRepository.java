package com.aboutme.springwebservice.domain.repository;

import com.aboutme.springwebservice.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
