package com.aboutme.springwebservice.mypage.entity;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = UserLevel.getWeeklyProgressing,
            procedureName = "aboutme_rds.getWeeklyProgressing",   //실제 DB쪽 프로시저 이름
            parameters = {
                    @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class),
                    @StoredProcedureParameter(name = "monday", mode = ParameterMode.IN, type = LocalDate.class),
                    @StoredProcedureParameter(name = "nextMonday", mode = ParameterMode.IN, type = LocalDate.class),
            }
    )
})


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="User_Level")
public class UserLevel {
    public static final String getWeeklyProgressing = "aboutme_rds.getWeeklyProgressing";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false)
    private long user_id;

    private int color;

    private int level;

    private int experience;

    @Builder
    public UserLevel(long user_id, int color, int level, int experience){
        this.user_id = user_id;
        this.color = color;
        this.level = level;
        this.experience = experience;
    }
}
