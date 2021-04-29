package com.aboutme.springwebservice.mypage.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProgressing {
    private int color;
    private int level;
    private int experience;

//    public ResponseUserLevel(UserLevel entity) {
//        id = entity.getSeq();
//        color = entity.getColor();
//        experience = entity.getExperience();
//        level = entity.getLevel();
//        user_id = entity.getUser_id();
//    }

//    public int getTotalExperience(){
//        return (this.getLevel()-1) * 100 + this.getExperience();
//    }
}
