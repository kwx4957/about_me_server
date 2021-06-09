package com.aboutme.springwebservice.board.model;

import com.aboutme.springwebservice.board.entity.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private long commentId;
    private long answerId;
    private long authorId;
    private String color;
    private String nickname;
    private String regDate;
    private String writtenDate;
    private String comment;

    public CommentDTO(BoardComment boardComment){
        this.commentId = boardComment.getSeq();
        this.answerId = boardComment.getCategoryLevelId();
        this.authorId = boardComment.getAuthorId();
        this.comment = boardComment.getComment();

        this.regDate = boardComment.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        LocalDateTime currentTime = LocalDateTime.now();

        if(currentTime.getYear() == boardComment.getRegDate().getYear() && currentTime.getMonth() == boardComment.getRegDate().getMonth() && currentTime.getDayOfMonth() == boardComment.getRegDate().getDayOfMonth()){
            if((currentTime.getHour() - boardComment.getRegDate().getHour()) > 0) {
                this.writtenDate = "약 " + (currentTime.getHour() - boardComment.getRegDate().getHour()) + "시간 전";
            }
            else if((currentTime.getHour()) - boardComment.getRegDate().getHour() == 0) {
                if ((currentTime.getMinute() - boardComment.getRegDate().getMinute()) > 0) {
                    this.writtenDate = "약 " + (currentTime.getMinute() - boardComment.getRegDate().getMinute()) + "분 전";
                } else if ((currentTime.getMinute() - boardComment.getRegDate().getMinute()) == 0) {
                    this.writtenDate = " 방금 전";
                }
            }
        }
        else {
            this.writtenDate = boardComment.getRegDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }

    public BoardComment toEntity() {
        return new BoardComment().builder()
                .categoryLevelId(answerId)
                .authorId(authorId)
                .comment(comment)
                .regDate(LocalDateTime.now())
                .build();
    }
}
