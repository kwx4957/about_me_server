package com.aboutme.springwebservice.board.entity;

import com.aboutme.springwebservice.board.model.response.ResponseBoardList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getLatestPost",
                procedureName = "aboutme_rds.getLatestPost",
                parameters = {}
        ),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="QnA_Category_Level")
@SqlResultSetMapping(
        name = "BoardList",
        classes = @ConstructorResult(
                targetClass = ResponseBoardList.class,
                columns = {
                        @ColumnResult(name = "answerId", type = Long.class),
                        @ColumnResult(name = "color", type = Integer.class),
                        @ColumnResult(name = "question", type = String.class),
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "nickname")
                }
        )
)
public class QnACategoryLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private long category_id;

    private String answer;

    private int level;

    private char share_yn;

    private int likes;

    private int scraps;

    private LocalDateTime reg_date;

    private LocalDateTime update_date;
}
