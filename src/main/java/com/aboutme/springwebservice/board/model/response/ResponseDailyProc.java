package com.aboutme.springwebservice.board.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDailyProc {
    int user; //질답 순서
    int cardSeq;//카드저장시 일련번호
    int quest_id; //질답 순서
    String question;
    String answer;
    int level;
    String isShare;
    int color;

    @Builder
    public ResponseDailyProc(int user,int cardSeq,int quest_id,String question,
            String answer,int level,String isShare,int color){
        this.user=user;
        this.cardSeq=cardSeq;
        this.quest_id=quest_id;
        this.question=question;
        this.answer=answer;
        this.level=level;
        this.isShare=isShare;
        this.color=color;
    }
    public ResponseDailyProc toEntity() {
        return ResponseDailyProc.builder()
                .user(user).cardSeq(cardSeq)
                .quest_id(quest_id).question(question)
                .answer(answer).level(level)
                .isShare(isShare).color(color)
                .build();
    }
}
