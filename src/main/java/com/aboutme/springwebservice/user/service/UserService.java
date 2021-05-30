package com.aboutme.springwebservice.user.service;

import com.aboutme.springwebservice.user.model.response.UserResponse;
import com.aboutme.springwebservice.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    private AppUserRepository userRepository;

    public UserResponse findUser(Long userNo) {
        return userRepository.findById(userNo)
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getProfileImage()))
                .orElse(null);
    }

    public List<UserResponse> findUserAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getProfileImage()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long userNo) {
        userRepository.deleteById(userNo);
    }
}
