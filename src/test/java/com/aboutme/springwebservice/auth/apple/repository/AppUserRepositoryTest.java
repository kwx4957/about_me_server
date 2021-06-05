package com.aboutme.springwebservice.auth.apple.repository;

import com.aboutme.springwebservice.auth.apple.domainModel.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {
    @Autowired
    private AppleUserRepository appleUserRepository;

    @Test
    @Rollback(value = false)
    public void user_info_insert(){
        User user = User.builder()
                .userId("js")
                .accesToken("accessTokenTest")
                .refreshToken("refreshTokenTest")
                .updateDate(new Date())
                .build();
        System.out.println(user);
        System.out.println(appleUserRepository);
        appleUserRepository.save(user);
    }

    @Test
    public void 유저_정보_조회() {
        User test = appleUserRepository.findByUserId("js");
        System.out.println(test);
    }
}
