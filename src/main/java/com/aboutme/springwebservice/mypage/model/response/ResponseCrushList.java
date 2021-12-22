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
    private boolean shareYN;
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
    public ResponseCrushList(QnACategoryLevel entity, int commentCount, int color, String question ,ResponseCrushList responseCrushList){
        this.question     = question;
        this.color        = convertColorIntToString(color);
        this.boardSeq     = entity.getSeq();
        this.answer       = entity.getAnswer();
        if(entity.getShare_yn()=='N')
            this.shareYN     = false;
       else
            this.shareYN     = true;
        this.level        = entity.getLevel();
        this.likes        = entity.getLikes();
        this.scraps       = entity.getScraps();
        this.updateDate   = converTime(entity.getUpdate_date());
        this.commentCount = commentCount;
        this.hasLiked     = responseCrushList.hasLiked;
        this.hasScraped   =  responseCrushList.hasScraped;
    }

    private boolean convertType(int hasInform){
        return hasInform != 0;
    }

    public String converTime(LocalDateTime update_date){
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

    private String convertColorIntToString(int color) {
        String colorString = "";
        switch (color) {
            case 0:
                colorString = "red";
                break;
            case 1:
                colorString = "yellow";
                break;
            case 2:
                colorString = "green";
                break;
            case 3:
                colorString = "pink";
                break;
            case 4:
                colorString = "purple";
                break;
        }
        return colorString;
    }

}
