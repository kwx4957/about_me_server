package com.aboutme.springwebservice.message.entity;

import com.aboutme.springwebservice.domain.UserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private int color;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDateTime regDate;

    @ManyToOne(targetEntity = UserInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_user" ,nullable = false)
    private UserInfo aulthorId;

    @Builder
    public NotificationList(String message, int color, UserInfo aulthorId){
        this.message = message;
        this.color = color;
        this.aulthorId = aulthorId;
    }

}