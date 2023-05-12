package com.aboutme.springwebservice.message.entity;

import com.aboutme.springwebservice.domain.UserInfo;
import com.aboutme.springwebservice.domain.UserProfile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Notication_List")
public class NotificationList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "noti_message")
    private String message;

    @Column(name = "color")
    @ColumnDefault("6") //기본값이 6인 이유 신고의 경우에는 color가 불필요 null값 삽입이 int형에 따른 0이 삽입된다
    private int color;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDateTime regDate;

    @ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_user" ,nullable = false)
    private UserProfile aulthorId;

    @Builder
    public NotificationList(String message, int color, UserProfile aulthorId){
        this.message = message;
        this.color = color;
        this.aulthorId = aulthorId;
    }

}