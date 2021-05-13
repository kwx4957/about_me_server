package com.aboutme.springwebservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="Default_Reason_List")
public class DefaultReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column
    String reason;
    @Column
    LocalDate reg_date;


    @Builder
    public DefaultReason(int seq,String reason, LocalDate reg_date){
        this.seq=seq;
        this.reason = reason;
        this.reg_date = reg_date;
    }
}
