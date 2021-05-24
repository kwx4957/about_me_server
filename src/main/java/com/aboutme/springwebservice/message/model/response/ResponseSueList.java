package com.aboutme.springwebservice.message.model.response;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseSueList {
    long boardSeq;
    String sueReason;
    String contents;
    @Builder
    public ResponseSueList(QnACategoryLevel qnACategoryLevel, String sue,QnACategoryLevel contents){
        this.boardSeq=qnACategoryLevel.getSeq();
        this.sueReason=sue;
        this.contents=contents.getAnswer();
    }

}
