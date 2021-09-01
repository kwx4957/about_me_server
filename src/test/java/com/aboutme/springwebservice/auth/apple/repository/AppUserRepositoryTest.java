package com.aboutme.springwebservice.auth.apple.repository;

import com.aboutme.springwebservice.auth.apple.domainModel.User;
import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {
    @Autowired
    private UserProfileRepository appleUserRepository;

    @Test
    @Rollback(value = false)
    public void user_info_insert(){
        UserProfile userProfile = UserProfile.builder(2345L)
                .reg_date(LocalDateTime.now())
                .update_date(LocalDateTime.now())
                .build();

        System.out.println(userProfile);
        System.out.println(appleUserRepository);
        appleUserRepository.save(userProfile);
    }

    @Test
    public void 유저_정보_조회() {
        UserProfile test = appleUserRepository.findOneByUserID(123);
        System.out.println(test);
    }
}
