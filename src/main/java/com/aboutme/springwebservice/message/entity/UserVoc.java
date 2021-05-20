package com.aboutme.springwebservice.message.entity;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
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
@Table(name = "User_VOC")
public class UserVoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @OneToOne(targetEntity = DefaultReasonList.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reason_id" )
    private DefaultReasonList reasonId;

    @ManyToOne(targetEntity = QnACategoryLevel.class, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id" ,nullable = false)
    private QnACategoryLevel questionId;

    @ManyToOne(targetEntity = UserInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id" ,nullable = false)
    private UserInfo authorId;

    @CreatedDate
    @Column
    private LocalDateTime regDate;

    @Builder
    public  UserVoc(DefaultReasonList reasonId, QnACategoryLevel questionId, UserInfo authorId){
        this.reasonId=reasonId;
        this.questionId=questionId;
        this.authorId=authorId;
    }
}