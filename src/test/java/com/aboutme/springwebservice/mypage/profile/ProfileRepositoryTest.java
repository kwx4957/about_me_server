package com.aboutme.springwebservice.mypage.profile;

import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.mypage.repository.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfileRepositoryTest {
    @Autowired
    UserProfileRepository profileRepository;

    @Test
    public void 프로필정보_조회() {
        System.out.println(profileRepository.findOneByUserID(1).getIntro());
    }
}
