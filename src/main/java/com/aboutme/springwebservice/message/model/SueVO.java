package com.aboutme.springwebservice.message.model;

import lombok.Data;

@Data
public class SueVO {
    int suedUserId;
    long targetQuestionId;
    String sueReason;
    String sueType;
}
