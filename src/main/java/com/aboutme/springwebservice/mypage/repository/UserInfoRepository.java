package com.aboutme.springwebservice.mypage.repository;
import com.aboutme.springwebservice.mypage.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
