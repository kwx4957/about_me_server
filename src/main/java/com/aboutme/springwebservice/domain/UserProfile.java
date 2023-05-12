package com.aboutme.springwebservice.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Builder.Default
    @Column(name = "push_yn" , columnDefinition = "Character default Y")
    private Character push_yn = 'Y';

    @Column(name = "push_time")
    private String push_time;

    @Column(name = "reg_date")
    private LocalDateTime reg_date;

    @Column(name = "update_time")
    private LocalDateTime update_date;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column
    private String fcmToken;

    @Builder
    public static UserProfileBuilder builder(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("필수 파라미터 누락");
        }
        return UserProfileBuilder().userID(id);
    }
    public void savefcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }
    public void pushYes(){
        this.push_yn = 'Y';
    }
    public void pushNo(){
        this.push_yn = 'N';
    }
}
