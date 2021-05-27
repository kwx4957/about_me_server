package com.aboutme.springwebservice.message.model;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SueJudgeVO {
    long boardSeq;
    String sueReason;
    @Builder
    public SueJudgeVO(QnACategoryLevel qnACategoryLevel, String sue){
        this.boardSeq=qnACategoryLevel.getSeq();
        this.sueReason=sue;
    }

}
