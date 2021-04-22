package com.aboutme.springwebservice.mypage.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelfQuestRepository extends JpaRepository<SelfQuest,String> {

}
