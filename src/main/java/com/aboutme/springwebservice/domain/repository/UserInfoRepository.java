package com.aboutme.springwebservice.domain.repository;

import com.aboutme.springwebservice.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findOneBySeq(long userID);

    @Query(value = "SELECT ifnull(count(seq),0) FROM aboutme_rds.User_Info where email=?1 and name=?2",nativeQuery = true)
    int isAdmin(String email, String password);
}
