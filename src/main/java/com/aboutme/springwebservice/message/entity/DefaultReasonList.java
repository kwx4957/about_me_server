package com.aboutme.springwebservice.message.entity;

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
@Table(name = "Default_Reason_List")
public class DefaultReasonList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column
    private String reason;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDateTime regDate;

    public DefaultReasonList(String reason){
        this.reason=reason;
    }

}