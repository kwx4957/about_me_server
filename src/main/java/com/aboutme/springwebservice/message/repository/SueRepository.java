package com.aboutme.springwebservice.message.repository;

import com.aboutme.springwebservice.board.entity.BoardInteraction;
import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.message.entity.UserVoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SueRepository extends JpaRepository<UserVoc,Long>{

}
