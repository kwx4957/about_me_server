package com.aboutme.springwebservice.message.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class PushNotificationVO {
    String    pushContents;
    LocalTime notiTime;

}
