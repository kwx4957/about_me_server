package com.aboutme.springwebservice.message.model.response;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseSueList {
    long boardSeq;
    String title;
    String sueReason;
    String contents;
    String color;

    @Builder
    public ResponseSueList(QnACategoryLevel qnACategoryLevel, String sue, QnACategoryLevel contents, String color, String title){
        this.title = title;
        this.color = color;
        this.boardSeq = qnACategoryLevel.getSeq();
        this.sueReason = sue;
        this.contents = contents.getAnswer();
    }

}
