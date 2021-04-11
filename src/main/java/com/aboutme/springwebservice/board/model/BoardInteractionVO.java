package com.aboutme.springwebservice.board.model;

import lombok.Data;

@Data
public class BoardInteractionVO {
    String questionId;
    String authorId;
    String userId;
}
