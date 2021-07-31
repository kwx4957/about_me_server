package com.aboutme.springwebservice.message.repository;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.message.entity.NotificationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationList, Long> {

    List<NotificationList> findAllByAulthorId(UserInfo id);
}
