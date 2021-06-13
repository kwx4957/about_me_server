package com.aboutme.springwebservice.mypage.model.response;

import com.aboutme.springwebservice.board.entity.QnACategoryLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseCrushList {
    private long boardSeq;
    private String question;
    private String color;
    private String answer;
    private int level;
    private int likes;
    private int scraps;
    private int commentCount;
    private String updateDate;
    private boolean hasLiked;
    private boolean hasScraped;

    @Builder
    public ResponseCrushList(Long seq, int hasliked ,int hasscraped ){
        this.boardSeq = seq;
        this.hasLiked =convertType(hasliked);
        this.hasScraped = convertType(hasscraped);
    }

    @Builder
    public ResponseCrushList(QnACategoryLevel entity, int commentCount, String color, String question ,ResponseCrushList responseCrushList){
        this.question     = question;
        this.color        = color;
        this.boardSeq     = entity.getSeq();
        this.answer       = entity.getAnswer();
        this.level        = entity.getLevel();
        this.likes        = entity.getLikes();
        this.scraps       = entity.getScraps();
        this.updateDate   = converTime(entity.getUpdate_date());
        this.commentCount = commentCount;
        this.hasLiked     = responseCrushList.hasLiked;
        this.hasScraped   =  responseCrushList.hasScraped;
    }

    private boolean convertType(int hasInform){
        if(hasInform == 0) {
            return false;
        }else {
            return true;
        }
    }

    private String converTime(LocalDateTime update_date){
        LocalDateTime currentTime= LocalDateTime.now();
        if((currentTime.getDayOfYear() - update_date.getDayOfYear())== 0) {
            if ((currentTime.getHour() - update_date.getHour()) > 0) {
                updateDate = "약 " + (currentTime.getHour() - update_date.getHour()) + "시간 전";
            } else if ((currentTime.getHour() - update_date.getHour()) == 0) {

                if ((currentTime.getMinute() - update_date.getMinute()) > 0) {
                    updateDate = "약 " + (currentTime.getMinute() - update_date.getMinute()) + "분 전";
                } else if ((currentTime.getMinute() - update_date.getMinute()) == 0) {
                    updateDate = " 방금 전";
                }
            }
        }
        else {
            return update_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return updateDate;
    }
}
