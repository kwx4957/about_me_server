package com.aboutme.springwebservice.mypage.model;

import com.aboutme.springwebservice.mypage.entity.UserLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLevelDTO {
    private Long seq;
    private int color;
    private int level;
    private int experience;
    private long user_id;

    public UserLevelDTO(UserLevel entity) {
        this.seq = entity.getSeq();
        this.color = entity.getColor();
        this.level = entity.getLevel();
        this.experience = entity.getExperience();
        this.user_id = entity.getUser_id();
    }

    public UserLevel toEntity() {
        return UserLevel.builder()
                .color(color)
                .level(level)
                .experience(experience)
                .user_id(user_id)
                .build();
    }
}
