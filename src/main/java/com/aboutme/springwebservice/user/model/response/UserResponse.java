package com.aboutme.springwebservice.user.model.response;

import com.aboutme.springwebservice.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Data
public class UserResponse {
    private UserProfile userProfile;
}
