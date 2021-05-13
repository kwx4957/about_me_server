package com.aboutme.springwebservice.board.model;

import lombok.Data;

@Data
public class BoardInteractionVO {
    int userId;
    int authorId;
    long questId;
}
