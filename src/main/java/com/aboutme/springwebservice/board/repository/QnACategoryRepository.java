package com.aboutme.springwebservice.board.repository;

import com.aboutme.springwebservice.board.entity.QnACategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnACategoryRepository extends JpaRepository<QnACategory, Long> {
}
