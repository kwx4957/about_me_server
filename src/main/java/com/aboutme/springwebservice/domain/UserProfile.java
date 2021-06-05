package com.aboutme.springwebservice.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder(builderMethodName = "UserProfileBuilder")
@Table(name="User_Profile")
@ToString
@DynamicUpdate
public class UserProfile {
    @Id
    @Column(name = "user_id")
    private long userID;

    @Column(name = "introduce")
    private String intro;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "color")
    private Integer color;

    @Column(name = "theme_comment")
    private Integer themeComment;

    @Column(name = "push_yn")
    private String push_yn;

    @Column(name = "push_time")
    private String push_time;

    @Column(name = "reg_date")
    private LocalDateTime reg_date;

    @Column(name = "update_time")
    private LocalDateTime update_date;

    @Builder
    public static UserProfileBuilder builder(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("필수 파라미터 누락");
        }
        return UserProfileBuilder().userID(id);
    }
}
