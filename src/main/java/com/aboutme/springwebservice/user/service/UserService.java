package com.aboutme.springwebservice.user.service;

import com.aboutme.springwebservice.domain.repository.UserProfileRepository;
import com.aboutme.springwebservice.user.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserResponse findUser(Long userNo) {
        return new UserResponse(userProfileRepository.findOneByUserID(userNo));
    }

    @Transactional
    public void deleteUser(Long userNo) {
        userProfileRepository.deleteById(userNo);
    }
}
