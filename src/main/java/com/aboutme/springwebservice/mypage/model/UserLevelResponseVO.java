package com.aboutme.springwebservice.mypage.model;

import com.aboutme.springwebservice.mypage.domain.UserLevel;
import lombok.Getter;

@Getter
public class UserLevelResponseVO {
    private Long id;
    private int color;
    private int level;
    private int experience;
    private long user_id;

    public UserLevelResponseVO(UserLevel entity) {
        id = entity.getId();
        color = entity.getColor();
        experience = entity.getExperience();
        level = entity.getLevel();
        user_id = entity.getUser_id();
    }

    public int getTotalExperience(){
        return (this.getLevel()-1) * 100 + this.getExperience();
    }
}
