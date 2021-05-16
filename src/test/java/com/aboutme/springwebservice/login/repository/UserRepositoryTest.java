package com.aboutme.springwebservice.login.repository;

import com.aboutme.springwebservice.login.domainModel.User;
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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

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
        System.out.println(userRepository);
        userRepository.save(user);
    }

    @Test
    public void 유저_정보_조회() {
        User test = userRepository.findByUserId("js");
        System.out.println(test);
    }
}
