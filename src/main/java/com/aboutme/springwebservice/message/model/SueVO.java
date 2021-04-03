package com.aboutme.springwebservice.message.model;

import lombok.Data;

@Data
public class SueVO {
    String suedUserId;
    String targetQuestionId;
    String suedReason;
}
