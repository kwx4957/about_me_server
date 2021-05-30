package com.aboutme.springwebservice.mypage.profile;

import com.aboutme.springwebservice.domain.UserProfile;
import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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

    @Test
    @Rollback(value = false)
    public void 프로필정보_업데이트() {
//        UserProfile profile = UserProfile.builder(6L)
//                .color(1)
//                .intro("인트로")
//                .nickname("닉네임")
//                .push_yn('y')
//                .themeComment(1)
//                .update_date(new LocalDateTime())
//                .build();
//        profileRepository.save(profile);
    }

}
