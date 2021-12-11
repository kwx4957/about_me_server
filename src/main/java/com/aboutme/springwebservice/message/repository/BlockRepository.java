package com.aboutme.springwebservice.message.repository;

import com.aboutme.springwebservice.message.entity.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface BlockRepository extends JpaRepository<UserBlock,Long> {

    @Query(value = "select count(id) from User_Block where offer_id=?1 and other_id=?2",nativeQuery = true)
    int userAlreadyBlock(long offer_id,long other_id);

    @Query(value = "insert into User_Block(other_id,offer_id) values(?2,?1) ",nativeQuery = true)
    void saveUserBlock(long offer_id,long other_id);
}
