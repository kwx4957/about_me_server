package com.aboutme.springwebservice.message.repository;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import com.aboutme.springwebservice.message.entity.UserVoc;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SueRepository extends JpaRepository<UserVoc,Long>{

    void deleteByquestionId(QnACategoryLevel qq);
}
