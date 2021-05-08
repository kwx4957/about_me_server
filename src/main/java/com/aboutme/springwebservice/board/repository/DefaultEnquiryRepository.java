package com.aboutme.springwebservice.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultEnquiryRepository extends JpaRepository<DefaultEnquiry, Long> {
    DefaultEnquiry findBySeq(long title_id);
}
