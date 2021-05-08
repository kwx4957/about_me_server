package com.aboutme.springwebservice.board.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = DefaultEnquiry.getDailyList,
                procedureName = "aboutme_rds.getDailyList",   //실제 DB쪽 프로시저 이름
                parameters = {
                        @StoredProcedureParameter(name = "_user", mode = ParameterMode.IN, type = Integer.class)
                })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="Default_Enquiry")
public class DefaultEnquiry {
    public static final String getDailyList = "aboutme_rds.getDailyList";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private int type;

    private int color;

    private Integer theme;

    private String question;

    private int sort;

    private LocalDateTime reg_date;
}
